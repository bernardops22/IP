class Functions {
	private static final int COLOR_DIFF = 10;
	//Devolver o valor máximo presente no vetor de inteiros
	static int maxNumber(int[] vector){
		int cont = 0;
		for (int i = 0; i!=vector.length;i++){
			if (vector[i] <0)
				throw new IllegalArgumentException("Os valores presentes no vetor de inteiros não podem ser negativos");
			if (vector[i] > cont)
				cont = vector[i];
		}
		return cont;
	}
	//Gráfico de colunas 2D verticais
	static ColorImage graph(int[] vec, int columnw, int space, Color c){
		if (columnw <=0)
			throw new IllegalArgumentException("A largura das colunas não pode ser inferior a 1!");
		if (space<0)
			throw new IllegalArgumentException("O espaço entre colunas não pode ser inferior a 0!");
		int spaceBetweenColumns = space;
		int columnNumber = 0;
		ColorImage img = new ColorImage (vec.length*(columnw+space)+space,maxNumber(vec));
		while (columnNumber<vec.length){
			for (int x = 0; x!=columnw; x++){				
					//Exceções à regra
					if (vec[columnNumber] == 1){
						for (int y =img.getHeight()-1; y!=img.getHeight()-2; y--)
							img.setColor(x+space,y,c);
					}
					if(vec[columnNumber] > 1){
						for (int y =img.getHeight()-1; y!=img.getHeight()-vec[columnNumber]; y--)
							img.setColor(x+space,y,c);
					}
			}
			space = space + spaceBetweenColumns + columnw;
			columnNumber++;
		}
		return img;
	}
	//Função auxiliar para escurecer cor	
	static Color darken(Color c, int value){
		if (value<0)
			value = 0;
		int red = c.getR()-value;
		int green = c.getG()-value;
		int blue = c.getB()-value;
		if (red<0)
			red = 0;
		if (green<0)
			green = 0;
		if (blue<0)
			blue = 0;
		return new Color (red, green, blue);
	}
	//Gráfico de colunas 2D verticais suavizando os contornos das colunas
	static ColorImage smoothedGraph(int[] vec, int columnw, int space, Color c, int value){
		if (vec == null)
			throw new IllegalArgumentException("O vetor não pode ser nulo!");
		if (columnw <=0)
			throw new IllegalArgumentException("A largura das colunas não pode ser inferior a 1!");
		if (space<0)
			throw new IllegalArgumentException("O espaço entre colunas não pode ser inferior a 0!");
		if (value <0)
			throw new IllegalArgumentException("O gradiente não pode ser inferior a 0!");
		if (value>columnw/2)
			throw new IllegalArgumentException("O gradiente não pode ser superior a metade do tamanho da coluna!");
		int spaceBetweenColumns = space;
		int columnNumber = 0;
		int grad = 0;
		ColorImage img = new ColorImage (vec.length*(columnw+space)+space,maxNumber(vec));
		while (columnNumber<vec.length){
			//Interior
			if(vec[columnNumber]>0){
				for (int x = 0; x!=columnw; x++){
					for (int y =img.getHeight()-1; y!=img.getHeight()-vec[columnNumber]; y--)
						img.setColor(x+space,y,c);
				}
			}
			//Verticais Iniciais
			grad = COLOR_DIFF*value;
			for (int x = 0; x!=value; x++){
				Color c1 = darken(c,grad);
				for (int y =img.getHeight()-vec[columnNumber]; y!=img.getHeight(); y++){
					img.setColor(x+space,y,c1);
				}
				grad = grad-COLOR_DIFF;
			}	
			//Verticais Finais
			grad = COLOR_DIFF*value;
			for (int x = value; x!=0; x--){
				Color c1 = darken(c,grad);
				for (int y =img.getHeight()-vec[columnNumber]; y!=img.getHeight(); y++)
					img.setColor(x+space+columnw-value-1,y,c1);
				grad = grad-COLOR_DIFF;
			}
			//Horizontais
			grad = COLOR_DIFF*value;
			int error = 0;
			if (vec[columnNumber]==1){
				for (int x = 0; x!= columnw; x++)
					img.setColor(x+space,img.getHeight()-1,darken(c,grad));
			}
			else{
				for (int y= img.getHeight()-vec[columnNumber]; y!=img.getHeight()-vec[columnNumber]+value;y++){
					if(y<img.getHeight()-1){
						Color c1 = darken(c,grad);
						for (int x = error; x!=columnw-error; x++)
							img.setColor(x+space,y,c1);
						error++;
						grad = grad-COLOR_DIFF;
					}
				}
			}
			space = space + spaceBetweenColumns + columnw;
			columnNumber++;
		}
		return img;
	}
	//Função auxiliar para criar circulos
	static void circle(int radius, int xinicial, int yinicial, Color c,ColorImage img){
		for (int x = xinicial-radius; x!= xinicial+radius;x++){
			for (int y = yinicial-radius; y!=yinicial+radius; y++){
				if(y<=img.getHeight()-1){
					if((x-xinicial)*(x-xinicial)+(y-yinicial)*(y-yinicial)<radius*radius){
						img.setColor(x,y,c);
					}
				}
			}
		}
	}
	//Gráfico de dispersão
	static ColorImage scatterPlot(int vec[], int radius, int space, Color c){
		if (vec == null)
			throw new IllegalArgumentException("O vetor não pode ser nulo!");
		if (radius <=0)
			throw new IllegalArgumentException("O raio não pode ser inferior a 1!");
		if (space <0)
			throw new IllegalArgumentException("O espaçamento não pode ser negativo!");
		int spaceBetweenColumns = space;
		int circleNumber = 0;
		ColorImage img = new ColorImage (vec.length*(2*radius+space)+space, maxNumber(vec)+radius);
		while (circleNumber<vec.length){
			circle(radius,space+radius,img.getHeight()-vec[circleNumber],c,img);
			space = space + spaceBetweenColumns + 2*radius;
			circleNumber++;
		}
		return img;		
	}
	//Rotação de 90º no sentido horário dando como argumento um gráfico
	static ColorImage invert90D(ColorImage img){
		ColorImage fin = new ColorImage(img.getHeight(),img.getWidth());
		for (int x = 0; x!= fin.getWidth();x++){
			for (int y = 0; y!= fin.getHeight();y++)
				fin.setColor(x,y,img.getColor(y,img.getHeight()-1-x));
		}
		return fin;
	}			
	//Função teste
	static void tests(){
		int[] vec = {100,0,50,70};
		Color blue = new Color(0,0,255);
		Color red = new Color(255,0,0);
		Color green = new Color(0,255,0);
//		graph(vec,20,30,c);
		
		graph(vec, 40, 20 , red);
		scatterPlot(vec,15,20,blue);
		smoothedGraph(vec,40,20,green,10);
		invert90D(smoothedGraph(vec,40,20,green,12));
	}
}