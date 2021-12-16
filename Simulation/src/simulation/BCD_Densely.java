package simulation;

public class BCD_Densely {
	/**
	 * AEI Grid configured as the following:
	 * a,b,c,d,e,f,g,h,i,j,k,m,0,1 
	 * 0,1,2,3,4,5,6,7,8,9,10,11,12,13
	 */
	int[][] aeiGrid = {
			{}, //000=bcdfgh0jkm
			{}, //001=bcdfgh100m
			{}, //010=bcdjkh101m
			{}, //011=bcd10h111m
			{}, //100=jkdfgh110m
			{}, //101=fgd01h111m
			{}, //110=jkd00h111m
			{}  //111=00d11h111m
	};
	
	/**
	 * vwxstGrid configured as the following:
	 * p,q,r,s,t,u,v,w,x,y,0,1
	 * 0,1,2,3,4,5,6,7,8,9,10,11
	 */
	int[][] vwxstGrid = {
			{}, //0xxxx=0pqr0stu0wxy
			{}, //100xx=0pqr0stu100y
			{}, //101xx=0pqr100u0sty
			{}, //110xx=100r0stu0pqy
			{}, //11100=100r100u0pqy
			{}, //11101=100r0pqu100y
			{}, //11110=0pqr100u100y
			{}  //11111=100r100y100y
	};
	
	public BCD_Densely() {
		//N/A
	}
	
	
}
