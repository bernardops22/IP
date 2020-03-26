class OverlayGraphics {

	int firstNullValue;
	Graphs[] vector = new Graphs[MAX];
	
	private static final int MAX = 50;
	
	//Construtores
	OverlayGraphics(Graphs graph){
		if (graph == null)
			throw new NullPointerException ("A imagem não pode ser nula!");
		else{
			this.vector[0] = graph;
			this.firstNullValue=1;
		}
	}
	OverlayGraphics (Graphs[] graphvector){
		if(graphvector == null)
			throw new NullPointerException ("O vetor não pode ser nulo!");
		else{
			if(graphvector.length>=this.vector.length)
				this.vector = new Graphs[(MAX*(graphvector.length/this.vector.length))+1];
			for (int x = 0; x!=graphvector.length;x++){
				this.vector[x] = graphvector[x];
			}
		}
		this.firstNullValue=graphvector.length;
	}
	
	//Função auxiliar que determina a primeira posição vazia do vetor
	void firstNullValue(){
		for (int x = 0; x!=this.vector.length;x++){
			if (this.vector[x] == null){
				this.firstNullValue = x;
				break;
			}
		}
	}
	//Armazenar uma pilha de fráficos
	void storeGraphics(Graphs[] stack){
		int val;
		if (stack == null)
			throw new NullPointerException ("O vetor não pode ser nulo!");
		else{
			firstNullValue();
			val = this.firstNullValue;
			if(this.vector.length-val<=stack.length){
				Graphs [] vec = new Graphs[MAX*2];
				for (int x = 0; x!=this.vector.length;x++)
					vec[x] = this.vector[x];
				this.vector = vec;
				for (int x = 0; x!=stack.length;x++)						
					this.vector[x+val] = stack[x];
			}
			else{
				for (int x = 0; x!=stack.length;x++)						
						vector[x+val] = stack[x];
			}
		}
		firstNullValue();
	}	
	//Adicionar um gráfico no topo da pilha
	void addGraphToStack(Graphs img){
		firstNullValue();
		this.vector[this.firstNullValue] = img;
		firstNullValue();
	}
	//Remover um gráfico do topo da pilha
	void removeGraphOnTop(){
		firstNullValue();
		this.vector[this.firstNullValue-1]= null;
		firstNullValue();
	}
	//Obter o gráfico presente no topo da pilha
	Graphs getGraphOnTop(){
		firstNullValue();
		return this.vector[this.firstNullValue-1];
	}
	//Adicionar um gráfico numa dada posição
	void addGraphToPosition(Graphs graph, int position){
		firstNullValue();
		if (position>this.firstNullValue)
			throw new IllegalArgumentException("A posição tem de ser inferior a " + this.firstNullValue);
		else
			this.vector[position] = graph;
	}
	//Trocar de posição dois gráficos da pilha
	void changegraphPosition(int a, int b){
		firstNullValue();
		if (this.firstNullValue==1)
			throw new IllegalArgumentException("A pilha precisa ter pelo menos dois gráficos já gravados");
		else{
			if (a >this.firstNullValue || b >this.firstNullValue)
				throw new IllegalArgumentException("Não é possivel trocar gráficos entre posições que estejam vazias");
			else{
				this.vector[this.firstNullValue] = this.vector[a];
				this.vector[a] = this.vector[b];
				this.vector[b] = this.vector[this.firstNullValue];
				this.vector[this.firstNullValue] = null;
			}
		
		}
	}
	//Obter todos os gráficos sem título
	Graphs[] graphsWithNoTitle(){
		firstNullValue();
		int val = 0;
		for (int x = 0; x!=this.firstNullValue;x++){
			if(this.vector[x].title == null)
				val = val + 1;
		}
		Graphs[] nullGraphs = new Graphs[val];
		int i = 0;
		while (i<val){
			for (int x = 0; x!=this.firstNullValue;x++){
				if (this.vector[x].title==null){
					nullGraphs[i] = this.vector[x];
					i++;
				}
			}
		}
		return nullGraphs;
	}
	//Obter todos os gráficos com título
	Graphs[] graphsWithTitle(){
		firstNullValue();
		int val = 0;
		for (int x = 0; x!=this.firstNullValue;x++){
			if(this.vector[x].title != null)
				val = val + 1;
		}
		Graphs[] graphsWithTitle = new Graphs[val];
		int i = 0;
		while (i<val){
			for (int x = 0; x!=this.firstNullValue;x++){
				if (this.vector[x].title!=null){
					graphsWithTitle[i] = this.vector[x];
					i++;
				}
			}
		}
		return graphsWithTitle;
	}
	//Obter os gráficos por ordem alfabética de título dos gráficos
	Graphs[] graphsInOrder(){
		firstNullValue();
		int x;
		int i = 0;
		Graphs[] vector = new Graphs[this.firstNullValue];
		Graphs[] vectorTitle = graphsWithTitle();
		Graphs[] vectorNoTitle = graphsWithNoTitle();
		while (i < vectorTitle.length){
			for (x = 0; x!=vectorTitle.length-1;x++){
				if (vectorTitle.length == 1){
					vector[i] = vectorTitle[i];
					i++;
				}
				if(vectorTitle.length > 1){
					if (vectorTitle[x].title.compareTo(vectorTitle[x+1].title)<0){
						vector[i] = vectorTitle[x+1];
						vector [i+1] = vectorTitle[x];
						i++;
					}
					else{
						vector[i] = vectorTitle[x];
						i++;
					}
				}
			}
		}
		while (i < this.firstNullValue){
			for (x=0;x!=vectorNoTitle.length;x++){
				vector[i] = vectorNoTitle[x];
				i++;
			}
		}
		return vector;
	}
	//Obter a sobreposição de todas as imagens presentes no vetor
	ColorImage overlap(){
		firstNullValue();
		int largura = 0;
		int comprimento  = 0;
		for (int x = 0; x!=this.firstNullValue;x++){
			if(this.vector[x].img.getHeight()>largura)
				largura = this.vector[x].img.getHeight();
			if(this.vector[x].img.getWidth()>comprimento)
				comprimento = this.vector[x].img.getWidth();
		}
		ColorImage imagem = new ColorImage(comprimento, largura);
		for (int i = 0; i!=this.firstNullValue;i++){
			for (int x = 0; x!= this.vector[i].img.getWidth();x++){
				for (int y = 0; y!= this.vector[i].img.getHeight();y++){
					if(this.vector[i].img.getColor(x,y).getR() > 0 || this.vector[i].img.getColor(x,y).getG() > 0 || this.vector[i].img.getColor(x,y).getB() > 0 ){
						imagem.setColor(x+imagem.getWidth()-this.vector[i].img.getWidth(),y+imagem.getHeight()-this.vector[i].img.getHeight(),this.vector[i].img.getColor(x,y));
					}
				}
			}
		}
		return imagem;
	}
	//Obter a sobreposição rodada 90º
	ColorImage overlap90D(){
		ColorImage imgInicial = overlap();
		ColorImage imgFinal = new ColorImage(imgInicial.getHeight(),imgInicial.getWidth());
		for (int x = 0; x!= imgFinal.getWidth();x++){
			for (int y = 0; y!= imgFinal.getHeight();y++)
				imgFinal.setColor(x,y,imgInicial.getColor(y,imgInicial.getHeight()-1-x));
		}
		return imgFinal;
	}			

	//Função teste
	static ColorImage testes(){
		int[] vec = {40,20,10,120};
		Color c = new Color(255,250,3);
		Color c1 = new Color(255,0,0);
		ColorImage imagem = Functions.smoothedGraph(vec,10,10,c,5);
		ColorImage imagem1 = Functions.graph(vec,10,20,c);
		ColorImage imagem2 = Functions.scatterPlot(vec,4,12,c1);
		Graphs img = new Graphs(imagem, "Taxa de natalidade nos últimos anos", "Tempo", "Percentagem por cada 1000 habitantes");
//		img.setTransparent();
		Graphs img1 = new Graphs(imagem1, "aa", "bb", "cc");
//		img1.setTransparent();
		Graphs img2 = new Graphs(imagem2);
		img2.setTransparent();
		Graphs[] vector = {img1,img,img2};
		OverlayGraphics graphvector = new OverlayGraphics(vector);		
//		Graphs[] vector1 = {img1,img};
		
//		graphvector.storeGraphics(vector1);
//		graphvector.firstNullValue();
//		graphvector.addGraphToStack(img1);
//		graphvector.removeGraphOnTop();
//		return graphvector.getGraphOnTop();	
//		graphvector.addGraphToPosition(img2,0);
//		graphvector.changegraphPosition(1,2);
//		return graphvector.graphsWithNoTitle();
//		return graphvector.graphsWithTitle();
//		return graphvector.graphsInOrder();
//		return graphvector.overlap();
		return graphvector.overlap90D();
	}
}