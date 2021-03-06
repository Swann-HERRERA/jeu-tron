import static java.lang.Math.*;import static org.javascool.macros.Macros.*;import static org.javascool.macros.Stdin.*;import static org.javascool.macros.Stdout.*;import static org.javascool.proglets.codagePixels.Functions.*;public class JvsToJavaTranslated8 implements Runnable{  private static final long serialVersionUID = 8L;  public void run() {   try{ main(); } catch(Throwable e) {     if (e.toString().matches(".*Interrupted.*"))println("\n-------------------\nProgramme arr�t� !\n-------------------\n");    else println("\n-------------------\nErreur lors de l'ex�cution de la proglet\n"+org.javascool.core.Jvs2Java.report(e)+"\n-------------------\n");}  }public static void main(String[] usage) {    new org.javascool.widgets.MainFrame().reset("tron_fini", 700, 600, org.javascool.core.ProgletEngine.getInstance().setProglet("codagePixels").getProgletPane()).setRunnable(new JvsToJavaTranslated8());}

Boolean perdu=false,continuer=true,joueur1=true,joueur2=true,joueur3=true,joueur4=true;
String gagnant = "Egalit�",modeDeJeu="";

//direction motos
int directionJ1 =0,directionJ2=0,directionJ3=0,directionJ4=0;
int directionIA=random(1,4);

//Coordonn�es joueurs abcisses
int X1 = 5,X2=-5, X3 = -5,X4=5;
//Coordonn�es joueurs ordonn�es
int Y1=5,Y2= 5,Y3=-5,Y4= -5;

int scoreJ1=0,scoreJ2=0,scoreJ3=0,scoreJ4=0,scoreIA=0;


String menu(){  //menu de selection du mode de jeu
	int x=-45,y=36;
	dessinePhrase(x,y,"TRON","cyan",true,3);
	
	//mode de jeu solo
	x+=23;
	y-=32;
	dessinePhrase(x,y,"SOLO","black",false,2);
	
	//mode de jeu 2 joueurs
	y-=18;
	dessinePhrase(x,y,"DUEL","black",false,2);

	//mode de jeu 4 joueurs
	y-=18;
	dessinePhrase(x,y,"QUAD","black",false,2);

	//variables pour eviter les erreurs
	int flag=0;
	int dernierClicX = getX();
	int dernierClicY = getY();

	//boucle de choix utilisateur par clic souris
	while(flag==0){ sleep(1);
		
		if (dernierClicX==getX()&&dernierClicY==getY()){   //Anti-erreur
			flag=0;
		}else if(getY()>-9&&getY()<6){  		//Choix de jeu seul vs IA (zone sur toute la longueur)
			flag=1;
			return "solo";
		}else if (getY()>-27&&getY()<-12){   	//Choix de jeu 1 contre 1
			flag=1;
			return "duel";
		}else if(getY()>-45&&getY()<-30){		//Choix de jeu 1vs1vs1vs1
			flag=1;
			return "quad";
		}else{
			flag=0;
		}
	}
	return "erreur";
}

void main() {
	reset(50,50);
	modeDeJeu=menu();	//appel d'un choix de jeu avec fonction menu
 	
 	
 	//Ecoute des touches constament (si souris sur l'ecran)
	setKeyListener(
   		new Runnable() {
     		public void run() {
          		// Ici vient le code � ex�cuter quand une touche est enfonc�e
	          	String touche=getLastKey();
	          	echo("� : la touche '" + touche + "' a �t� d�tect�e");
	          	// echo affiche le resultat dans la console sans lui passer le focus.
	         		if (touche == "Haut") {
	      			directionJ1 =1;
	      		} else if (touche == "Bas") {
	    		     	directionJ1 =3;
	  			} else if (touche == "Droite") {
	  				directionJ1 =2;
	  	 		} else if (touche == "Gauche") {
	   	          	directionJ1 =4;
	          	} else if (touche.charAt(0) == 'z'||touche.charAt(0) == 'Z') {
	   	          	directionJ2 =1;
	   	          } else if (touche.charAt(0) == 's'||touche.charAt(0) == 'S') {
	   	          	directionJ2 =3;
	   	          } else if (touche.charAt(0) == 'd'||touche.charAt(0) == 'D') {
	   	          	directionJ2 =2;
	   	          } else if (touche.charAt(0) == 'q'||touche.charAt(0) == 'Q') {
	   	          	directionJ2 =4;
	   	          } else if (touche.charAt(0) == 'i'||touche.charAt(0) == 'I') {
	   	          	directionJ3 =1;
	   	          } else if (touche.charAt(0) == 'k'||touche.charAt(0) == 'K') {
	   	          	directionJ3 =3;
	   	          } else if (touche.charAt(0) == 'l'||touche.charAt(0) == 'L') {
	   	          	directionJ3 =2;
	   	          } else if (touche.charAt(0) == 'j'||touche.charAt(0) == 'J') {
	   	          	directionJ3 =4;
	   	          } else if (touche.charAt(0) == '8') {
	   	          	directionJ4 =1;
	   	          } else if (touche.charAt(0) == '5') {
	   	          	directionJ4 =3;
	   	          } else if (touche.charAt(0) == '6') {
	   	          	directionJ4 =2;
	   	          } else if (touche.charAt(0) == '4') {
	   	          	directionJ4 =4;
	   	          }
			}
	     }
	);

	
		while(continuer==true){ sleep(1); //boucle de jeu relancable
			
			//initialisation de l'ecran
	 		reset(50, 50);
			//message du d�but et attente que tout les joueurs soient pret
			if(modeDeJeu=="duel"){
				debut(2);
			}else if(modeDeJeu=="solo"){
				debut(1);
			}else if(modeDeJeu=="quad"){
				debut(4);
			}
			//ecran blanc pour jeu
			reset(25,25);
			//Fonction du jeu selon le nombre de joueurs avec en retour le gagnant
			if(modeDeJeu=="duel"){
				gagnant=duelGame();
			}else if(modeDeJeu=="solo"){
				gagnant=soloGame();
			}else if(modeDeJeu=="quad"){
				gagnant=quadGame();
			}
			//pause pour voir la fin du jeu
			sleep(1500);
			//ecran blanc pour message
			reset(50, 50);
			//message de fin
			gameOver();
			//test du perdant
			if(gagnant=="J1"){
				//affiche le nom du gagant: Winner(int numeroDuGagnant)
				afficheGagnant(1);
				scoreJ1++;
			}else if (gagnant=="J2"){
				//affiche le nom du gagant: Winner(int numeroDuGagnant)
				afficheGagnant(2);
				scoreJ2++;
			}else if (gagnant=="J3"){
				//affiche le nom du gagant: Winner(int numeroDuGagnant)
				afficheGagnant(3);
				scoreJ3++;}
			else if (gagnant=="J4"){
				//affiche le nom du gagant: Winner(int numeroDuGagnant)
				afficheGagnant(4);
				scoreJ4++;
			}else if (gagnant=="IA"){
				//affiche le nom du gagant: Winner(int numeroDuGagnant)
				afficheGagnant(5);
				scoreIA++;
			}else{
				//affiche le nom du gagant: Winner(int numeroDuGagnant)
				afficheGagnant(0);
			}
			score();
			
			sleep(1000);
			continuer=demandeContinuer();
			
		}

}


String soloGame() {
	//boucle de jeu
     while (perdu ==false) { sleep(1);
     	//test direction J1 + Changement de la coordon�es
     	if(directionJ1==1||directionJ2==1){
          	Y1 ++;
     	}else if(directionJ1==3||directionJ2==3){
          	Y1 --;
     	}else if(directionJ1==2||directionJ2==2){
            	X1 ++;
     	}else if (directionJ1==4||directionJ2==4){
            	X1 --;
          }
          //mouvements IA
		directionIA=deplacementIA();
		if(directionIA==1){
          	Y2 ++;
     	}else if(directionIA==3){
          	Y2 --;
     	}else if(directionIA==2){
            	X2 ++;
     	}else if (directionIA==4){
            	X2 --;
          }

          //test si collision ordi ou joueur
          perdu = (getPixel(X1, Y1) !=255||getPixel(X2, Y2) !=255)||(X1==X2&&Y1==Y2);
          if (perdu==true){
          	if((getPixel(X1, Y1) !=255&&getPixel(X2, Y2) !=255)||(X1==X2&&Y1==Y2)){
          		gagnant="EGAL";
          		if(getPixel(X1, Y1) !=255&&getPixel(X2, Y2) !=255){
          			explosion(X1,Y1);
          			explosion(X2,Y2);
          		}else if(X1==X2&&Y1==Y2){
          			explosion(X1,Y1);
          		}
          	}else if(getPixel(X1, Y1) !=255){
          		gagnant="IA";
          		explosion(X1,Y1);
          	}else if(getPixel(X2, Y2) !=255){
          		gagnant="J1";
          		explosion(X2,Y2);
          	}
          }else{
          	setPixel(X1, Y1, "red");
          	setPixel(X2, Y2, "green");
          	//vitesse de rafraichissement
          	sleep(100);
          }
	}
	return gagnant;
}

String duelGame() {
	//boucle de jeu
     while (perdu ==false) { sleep(1);
     	//test direction J1 + Changement de la coordon�es
     	if(directionJ1==1){
          	Y1 ++;
     	}else if(directionJ1==3){
          	Y1 --;
     	}else if(directionJ1==2){
            	X1 ++;
     	}else if (directionJ1==4){
            	X1 --;
          }
          //test direction J2 + Changement de la coordon�es
          if(directionJ2==1){
          	Y2 ++;
     	}else if(directionJ2==3){
            	Y2 --;
     	}else if (directionJ2==2){
            	X2 ++;
          }else if (directionJ2==4){
            	X2 --;
          }
          //test si l'un ou l'autre collision
          perdu = (getPixel(X1, Y1) !=255||getPixel(X2, Y2) !=255)||(X1==X2&&Y1==Y2);
          if (perdu==true){
          	if((getPixel(X1, Y1) !=255&&getPixel(X2, Y2) !=255)||(X1==X2&&Y1==Y2)){
          		gagnant="EGAL";
          		if(getPixel(X1, Y1) !=255&&getPixel(X2, Y2) !=255){
          			explosion(X1,Y1);
          			explosion(X2,Y2);
          		}else if(X1==X2&&Y1==Y2){
          			explosion(X1,Y1);
          		}
          	}else if(getPixel(X1, Y1) !=255){
          		gagnant="J2";
          		explosion(X1,Y1);
          	}else if(getPixel(X2, Y2) !=255){
          		gagnant="J1";
          		explosion(X2,Y2);
          	}
          }else{
          	setPixel(X1, Y1, "red");
          	setPixel(X2, Y2, "blue");
          	//vitesse de rafraichissement
          	sleep(70);
          }
	}
	return gagnant;
}

String quadGame(){
//boucle de jeu
     while (perdu ==false) { sleep(1);
     	//test direction J1 + Changement de la coordon�es
     	if(joueur1==true){
	     	if(directionJ1==1){
	          	Y1 ++;
	     	}else if(directionJ1==3){
	          	Y1 --;
	     	}else if(directionJ1==2){
	            	X1 ++;
	     	}else if (directionJ1==4){
	            	X1 --;
	          }
     	}
          //test direction J2 + Changement de la coordon�es
          if(joueur2==true){
	          if(directionJ2==1){
	          	Y2 ++;
	     	}else if(directionJ2==3){
	            	Y2 --;
	     	}else if (directionJ2==2){
	            	X2 ++;
	          }else if (directionJ2==4){
	            	X2 --;
	          }
          }
     	//test direction J3 + Changement de la coordon�es
     	if(joueur3==true){
	     	if(directionJ3==1){
	          	Y3 ++;
	     	}else if(directionJ3==3){
	          	Y3 --;
	     	}else if(directionJ3==2){
	            	X3 ++;
	     	}else if (directionJ3==4){
	            	X3 --;
	          }
     	}
          //test direction J4 + Changement de la coordon�es
          if(joueur4==true){
	          if(directionJ4==1){
	          	Y4 ++;
	     	}else if(directionJ4==3){
	            	Y4 --;
	     	}else if (directionJ4==2){
	            	X4 ++;
	          }else if (directionJ4==4){
	            	X4 --;
	          }
          }
          if((getPixel(X1, Y1) !=255)||(X1==X2&&Y1==Y2)||(X1==X3&&Y1==Y3)||(X1==X4&&Y1==Y4)){
          	joueur1=false;
          }
          if((getPixel(X2, Y2) !=255)||(X2==X1&&Y2==Y1)||(X2==X3&&Y2==Y3)||(X2==X4&&Y2==Y4)){
          	joueur2=false;
          }
          if((getPixel(X3, Y3) !=255)||(X3==X1&&Y3==Y1)||(X2==X3&&Y2==Y3)||(X3==X4&&Y3==Y4)){
          	joueur3=false;
          }
          if((getPixel(X4, Y4) !=255)||(X4==X2&&Y4==Y2)||(X4==X3&&Y4==Y3)||(X1==X4&&Y1==Y4)){
          	joueur4=false;
          }
          //test si il y a collision
		if((joueur1?1:0)+(joueur2?1:0)+(joueur3?1:0)+(joueur4?1:0)==1){
			perdu=true;
			if(joueur1==true){
				explosion(X2,Y2);
				explosion(X3,Y3);
				explosion(X4,Y4);
				gagnant="J1";
			}else if(joueur2==true){
				explosion(X1,Y1);
				explosion(X3,Y3);
				explosion(X4,Y4);
				gagnant="J2";
			}else if(joueur3==true){
				explosion(X1,Y1);
				explosion(X2,Y2);
				explosion(X4,Y4);
				gagnant="J3";
			}else if(joueur4==true){
				explosion(X1,Y1);
				explosion(X2,Y2);
				explosion(X3,Y3);
				gagnant="J4";
			}
		}else if((joueur1?1:0)+(joueur2?1:0)+(joueur3?1:0)+(joueur4?1:0)==0){
			perdu=true;
			explosion(X1,Y1);
          	explosion(X2,Y2);
			explosion(X3,Y3);
			explosion(X4,Y4);
			gagnant="EGAL";
		}else{
          	setPixel(X1, Y1, "red");
          	setPixel(X2, Y2, "blue");
          	setPixel(X3, Y3, "orange");
          	setPixel(X4, Y4, "magenta");
          	//vitesse de rafraichissement
          	sleep(100);
          }
	}
	return gagnant;
}


int deplacementIA(){
	int xTest=X2;
	int yTest=Y2;
	int obstacle[]={0,0,0,0};
	int obstacleMin=11,flag=1,directionTest=0;
	for(int j=1;j<=4;j++){
		xTest=X2;
		yTest=Y2;
		for(int i=1;i<=2;i++){
				
			if(j==1&&j!=directionIA+2&&j!=directionIA-2){
				yTest++;
			}else if (j==2&&j!=directionIA+2&&j!=directionIA-2){
				xTest++;
			}else if (j==3&&j!=directionIA+2&&j!=directionIA-2){
				yTest--;
			}else if (j==4&&j!=directionIA+2&&j!=directionIA-2){
				xTest--;
			}
			if (getPixel(xTest, yTest)!=255){
				obstacle[j-1]++;
			}
			
		}
	}
	
	for(int k=1;k<=4;k++){
		if(k!=directionIA-2&&k!=directionIA+2){
			if(obstacle[k-1]<obstacleMin){
				obstacleMin=obstacle[k-1];
				directionIA=k;
			}else if(obstacle[k-1]==obstacleMin){
				if(random(1,71)<=2){
					directionIA=k;
				}
			}
		}
	}
	
	return directionIA;
}



void ligne(int x1,int y1,int x2,int y2,String couleur,Boolean gras){ //Trace une ligne entre deux points
	int distancex=x2-x1;
	int distancey=y2-y1;
	int distance=0;
	if(abs(distancex)>abs(distancey)){
		distance=abs(distancex);
	}else{
		distance=abs(distancey);
	}
	for(int i=0;i<distance+1;i++){
		setPixel(x1+i*distancex/distance,y1+i*distancey/distance,couleur);
		if (gras==true){
			setPixel(x1+1+i*distancex/distance,y1+i*distancey/distance,couleur);
			setPixel(x1+i*distancex/distance,y1+1+i*distancey/distance,couleur);
		}
	}
}

void dessineSegment(int x,int y,int segment,String couleur,Boolean gras,int police){ //donne les coordonn�es des points entre lequelles faire la ligne
		 int[][]matriceCoord={													//� la fonction ligne a partir des coordonn�es de base et du segment a tracer
		{0,0,10,0},
		{10,0,20,0},
		{0,0,0,-10},
		{0,0,10,-10},
		{10,0,10,-10},
		{20,0,10,-10},
		{20,0,20,-10},
		{0,-10,10,-10},
		{10,-10,20,-10},
		{0,-10,0,-20},
		{0,-20,10,-10},
		{10,-10,10,-20},
		{10,-10,20,-20},
		{20,-10,20,-20},
		{0,-20,10,-20},
		{10,-20,20,-20},
		{10,0,20,-10},
		{20,-10,10,-20},
		{10,-20,0,-10},
		{0,-10,10,0}
		};
		int x1=matriceCoord[segment][0];
		int y1=matriceCoord[segment][1];
		int x2=matriceCoord[segment][2];
		int y2=matriceCoord[segment][3];
		if(police==2){
			x1/=2;
			y1/=2;
			x2/=2;
			y2/=2;
		}else if (police==1){
			x1/=5;
			y1/=5;
			x2/=5;
			y2/=5;
		}
		ligne(x+x1,y+y1,x+x2,y+y2,couleur,gras);
}

void dessineLettre(int x,int y,String lettre,String couleur,Boolean gras,int police){
	for(int i=0;i<lettre.length();i++){
		char l=lettre.charAt(i);
		int n=l-'a';
		dessineSegment(x,y,n,couleur,gras,police);
	}
}

void dessinePhrase(int x,int y,String phrase,String couleur,Boolean gras,int police){ //assemble les lettres et ins�re les espaces
	for(int i=0;i<phrase.length();i++){									 //selon la police utilis�e
		char l=phrase.charAt(i);
		dessineLettre(x,y,avoirSegments(l),couleur,gras,police);
		if(police==2){
			x+=12;
		}else if(police==1){
			x+=6;
		}else{
			x+=24;
		}
	}
}


String avoirSegments(char lettre){
	//afficheur 20 segment (avec une lettre pour chaque segment qui renvoie a la matrice matriceCoord)
	if(lettre=='A') return "abcghijn";	
	if(lettre=='B') return "abcgijnop";
	if(lettre=='C') return "abcjop";	
	if(lettre=='D') return "acjoqr";
	if(lettre=='E') return "bachjop";
	if(lettre=='F') return "abchj";
	if(lettre=='G') return "abcjinop";
	if(lettre=='H') return "cjhign";
	if(lettre=='I') return "abelop";
	if(lettre=='J') return "abelo";
	if(lettre=='K') return "chjfm";
	if(lettre=='L') return "cjop";
	if(lettre=='M') return "jcdfgn";
	if(lettre=='N') return "cjdmng";
	if(lettre=='O') return "abcgjnop";
	if(lettre=='P') return "abcghij";
	if(lettre=='Q') return "qrstm";
	if(lettre=='R') return "abcghimj";
	if(lettre=='S') return "abchinpo";
	if(lettre=='T') return "abel";
	if(lettre=='U') return "cjopng";
	if(lettre=='V') return "cjkf";
	if(lettre=='W') return "cjkmng";
	if(lettre=='X') return "dfkm";
	if(lettre=='Y') return "dfl";
	if(lettre=='Z') return "abfkpo";
	if(lettre=='1') return "fgn";
	if(lettre=='2') return "bgilp";
	if(lettre=='3') return "bginp";
	if(lettre=='4') return "egin";
	if(lettre=='5') return "beinp";
	if(lettre=='6') return "beinlp";
	if(lettre=='7') return "bgn";
	if(lettre=='8') return "begnilp";
	if(lettre=='9') return "beginp";
	if(lettre=='0') return "beglnp";
	if(lettre==' ') return "";
	
	return "";
}

void gameOver(){						//ecrit GAME OVER
	int x=-45,y=48;
	dessinePhrase(x,y,"GAME","black",true,3);
	y-= 24;
	dessinePhrase(x,y,"OVER","cyan",true,3);
}

void afficheGagnant(int gagnant){
	String couleur="";
	int x=0,y=1;
	if (gagnant!=0){
		x-=39;
		if (gagnant==1){
			dessinePhrase(x,y,"J1 WINS","red",false,2);
		}else if (gagnant==2){
			dessinePhrase(x,y,"J2 WINS","blue",false,2);
		}else if (gagnant==4){
			dessinePhrase(x,y,"J4 WINS","magenta",false,2);
		}else if (gagnant==3){
			dessinePhrase(x,y,"J3 WINS","orange",false,2);
		}else if (gagnant==5){
			dessinePhrase(x,y,"IA WINS","green",false,2);
		}
	}else{
		//si egalite
		x-=22;
		dessinePhrase(x,y,"EGAL","black",false,2);
	}
	
}

void score(){
	int scoreX=-40,scoreY=-27;
	dessinePhrase(scoreX,scoreY,"SCORE J1 "+scoreJ1,"red",false,1);
	scoreY-=6;
	if(modeDeJeu=="duel"){
		dessinePhrase(scoreX,scoreY,"SCORE J2 "+scoreJ2,"blue",false,1);	
	}else if(modeDeJeu=="solo"){
		dessinePhrase(scoreX,scoreY,"SCORE IA "+scoreIA,"green",false,1);	
	}else if(modeDeJeu=="quad"){
		dessinePhrase(scoreX,scoreY,"SCORE J2 "+scoreJ2,"blue",false,1);
		scoreY-=6;
		dessinePhrase(scoreX,scoreY,"SCORE J3 "+scoreJ3,"orange",false,1);
		scoreY-=6;
		dessinePhrase(scoreX,scoreY,"SCORE J4 "+scoreJ4,"magenta",false,1);
	}
}

void debut(int joueurs){
	int x=-45,y=36;
	dessinePhrase(x,y,"TRON","cyan",true,3);
	
	y=12;


	if(modeDeJeu!="solo"){
		if(modeDeJeu=="duel"){x+=19;}
		x+=10;
		dessinePhrase(x,y,"J1","red",false,1);
		x+=20;
		dessinePhrase(x,y,"J2","blue",false,1);
		if(modeDeJeu=="quad"){
			x+=20;
			dessinePhrase(x,y,"J3","orange",false,1);
			x+=20;
			dessinePhrase(x,y,"J4","magenta",false,1);
		}
	}else{
		x+=29;
		dessinePhrase(x,y,"J1","red",false,1);
		x+=20;
		dessinePhrase(x,y,"IA","green",false,1);
	}
	y=0;
	x=-28;
	dessinePhrase(x,y,"PRESS","black",false,2);
	y-=12;
	x=-11;
	dessinePhrase(x,y,"TO","black",false,2);
	y-=12;
	x=-22;
	dessinePhrase(x,y,"PLAY","black",false,2);
	if(joueurs==2){
		while(directionJ1==0 || directionJ2==0){ sleep(1);
			sleep(50);
		}
	}else if(joueurs==1) {
		while(directionJ1==0){ sleep(1);
			sleep(50);
		}
	}else if(joueurs==4){
		while(directionJ1==0 || directionJ2==0||directionJ3==0 || directionJ4==0){ sleep(1);
			sleep(50);
		}
	}
}

void explosion (int x,int y){			//dessine l'explosion a la mort du pilote
	setPixel(x,y,"yellow");
	
	setPixel(random(x-2,x),random(y-2,y),"orange");
	setPixel(random(x-2,x),random(y,y+2),"orange");	
	setPixel(random(x,x+2),random(y-2,y),"orange");
	setPixel(random(x,x+2),random(y,y+2),"orange");
		
	setPixel(random(x-3,x-2),random(y+2,y+3),"red");
	setPixel(random(x-3,x-2),random(y-1,y+1),"black");
	setPixel(random(x-3,x-2),random(y-3,y-2),"red");
	setPixel(random(x-1,x+1),random(y+2,y+3),"red");
	setPixel(random(x-1,x+1),random(y-3,y-2),"black");
	setPixel(random(x+2,x+3),random(y+2,y+3),"red");
	setPixel(random(x+2,x+3),random(y-1,y+1),"black");
	setPixel(random(x+2,x+3),random(y-3,y-2),"red");
}

Boolean demandeContinuer(){
	int flag=0;
	int lastClicX=getX();
	int lastClicY=getY();
	while(flag==0){ sleep(1);
		
		int x=-26,y=-12;
		dessinePhrase(x,y,"CONTINUER","gray",false,1);
		x=-19; y-=8;
		dessinePhrase(x,y,"OUI","gray",false,1);
		x+=24;
		dessinePhrase(x,y,"NON","gray",false,1);
		if (lastClicX==getX()&&lastClicY==getY()){
			flag=0;
		}else if(getX()>-21&&getX()<-2){
			flag=1;
			perdu=false;
			continuer=true;
			gagnant = "Egalit�";
			directionJ1 =0;
			directionJ2=0;
			directionJ3=0;
			directionJ4=0;
			X1=5;
			X2=-5;
			X3=-5;
			X4=5;
			Y1=5;
			Y2=5;
			Y3=-5;
			Y4=-5;
			joueur1=true;
			joueur2=true;
			joueur3=true;
			joueur4=true;
			return true;
		}else if (getX()>2&&getX()<21){
			flag=1;
			reset(25,25);
			dessinePhrase(-22,10,"GG","black",true,3);
			return false;
		}else{
			flag=0;
		}
	}
	return false;
}


}
