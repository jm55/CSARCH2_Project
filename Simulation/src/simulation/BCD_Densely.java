package simulation;

public class BCD_Densely {
	/**
	 * aeiGrid configured as the following:
	 * a,b,c,d,e,f,g,h,i,j,k,m,0,1 
	 * 0,1,2,3,4,5,6,7,8,9,10,11,12,13
	 */
	private int[][] aeiGrid = {
			{1,2,3,5,6,7,12,9,10,11}, //000=bcdfgh0jkm
			{1,2,3,5,6,7,13,12,12,11}, //001=bcdfgh100m
			{1,2,3,9,10,7,13,12,13,11}, //010=bcdjkh101m
			{1,2,3,13,12,7,13,13,13,11}, //011=bcd10h111m
			{9,10,3,5,6,7,13,13,12,11}, //100=jkdfgh110m
			{5,6,3,12,13,7,13,13,13,11}, //101=fgd01h111m
			{9,10,3,12,12,7,13,13,13,11}, //110=jkd00h111m
			{12,12,3,13,13,7,13,13,13,11}  //111=00d11h111m
	};
	/**
	 * vwxstGrid configured as the following:
	 * p,q,r,s,t,u,v,w,x,y,0,1
	 * 0,1,2,3,4,5,6,7,8,9,10,11
	 */
	private int[][] vwxstGrid = {
			{10,0,1,2,10,3,4,5,10,7,8,9}, //0xxxx=0pqr0stu0wxy
			{10,0,1,2,10,3,4,5,11,10,10,9}, //100xx=0pqr0stu100y
			{10,0,1,2,11,10,10,5,10,3,4,9}, //101xx=0pqr100u0sty
			{11,10,10,2,10,3,4,5,10,0,1,9}, //110xx=100r0stu0pqy
			{11,10,10,2,11,10,10,5,10,0,1,9}, //11100=100r100u0pqy
			{11,10,10,2,10,0,1,5,11,10,10,9}, //11101=100r0pqu100y
			{10,0,1,2,11,10,10,5,11,10,10,9}, //11110=0pqr100u100y
			{11,10,10,2,11,10,10,9,11,10,10,9}  //11111=100r100y100y
	};
	private char[] aeiReference = {'a','b','c','d','e','f','g','h','i','j','k','m','0','1'}; 
	private char[] vwxstReference = {'p','q','r','s','t','u','v','w','x','y','0','1'};
	
	public BCD_Densely() {
		//N/A
		TestGrid();
	}
	
	private void TestGrid() {
		//AEI
		System.out.println("aeiGrid:");
		for(int i = 0; i < aeiGrid.length; i++) {
			for(int j = 0; j < aeiGrid[i].length; j++) {
				System.out.print(aeiReference[aeiGrid[i][j]]);
				if(j < aeiGrid[i].length-1)
					System.out.print(", ");
			}
			System.out.println("");
		}
		//VWXST
		System.out.println("vwxstGrid:");
		for(int i = 0; i < vwxstGrid.length; i++) {
			for(int j = 0; j < vwxstGrid[i].length; j++) {
				System.out.print(vwxstReference[vwxstGrid[i][j]]);
				if(j < vwxstGrid[i].length-1)
					System.out.print(", ");
			}
			System.out.println("");
		}
	}
}
