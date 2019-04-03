class Prime {

	public static boolean prime(long a){
	   if(a == 2){
	      return true;
	   }else if(a <= 1 || a % 2 == 0){
	      return false;
	   }
	   long max = (long)Math.sqrt(a);
	   for(long n= 3; n <= max; n+= 2){
	      if(a % n == 0){ return false; }
	   }
	   return true;
	}

	public static void main(String[] args) {
		if (prime(7)) {
			System.out.println("7 is prime");
		}
		if (!prime(8)) {
			System.out.println("8 is not prime");
		}
	}
}
