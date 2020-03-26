class Graphs {
	
	final ColorImage img;
	String title;
	String xtitle;
	String ytitle;
	
	private static final Color BLACK = new Color (0,0,0);
	
	Graphs(ColorImage img, String title, String xtitle, String ytitle){
		this.img = img;
		this.title = title;
		this.xtitle = xtitle;
		this.ytitle = ytitle;
	}
	Graphs(ColorImage img){
		this.img = img;
		this.title = null;
		this.xtitle = null;
		this.ytitle = null;
	}
	void addOrModifyTitle(String title){
		this.title = title;
	}
	void addOrModifyXTitle(String xtitle){
		this.xtitle = xtitle;
	}
	void addOrModifyYTitle(String ytitle){
		this.ytitle = ytitle;
	}
	void setTransparent(){
		for (int x = 0; x<img.getWidth();x++){
			for (int y = 0; y<img.getHeight();y++){
				if (x%2!=0 && y%2==0)
					img.setColor(x,y,BLACK);
				if (x%2==0 && y%2!=0)
					img.setColor(x,y,BLACK);
			}
		}
	}
	ColorImage getTransparentImage(){
		return this.img;		
	}
	String graphInfo(){
		if(title == null & xtitle == null & ytitle == null)
			throw new IllegalStateException ("O gráfico pedido não possui título nem os eixos legendado.");
		else{
			String txt = "Title: " + this.title + "; " + "X axis title: " + this.xtitle + "; " + "Y axis title: " + this.ytitle;
			return txt;
		}
	}
	static ColorImage test(){
		int[] vec = {100,100,50,70};
		Color c = new Color(255,0,0);
		ColorImage imagem = Functions.smoothedGraph(vec,40,20,c,12);
		Graphs img = new Graphs(imagem, "Taxa de natalidade nos últimos anos", "Tempo", "Percentagem por cada 1000 habitantes");
		img.setTransparent();
		return img.getTransparentImage();
//		return img.graphInfo();
	}
}