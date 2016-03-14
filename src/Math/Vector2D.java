package Math;


import java.lang.Math;

public class Vector2D {

   protected double x;
   protected double y;

   // Constructor methods ....

   public Vector2D() {
      x = y = 0.0;
   }

   public Vector2D( double dX, double dY ) {
      this.x = dX;
      this.y = dY;
   }

   // Convert vector to a string ...
    
   public String toString() {
      return "Vector2D(" + x + ", " + y + ")";
   }

   // Compute magnitude of vector ....
 
   public double length() {
      return Math.sqrt ( x*x + y*y );
   }

   // Sum of two vectors ....

   public Vector2D add( Vector2D v1 ) {
       Vector2D v2 = new Vector2D( this.x + v1.x, this.y + v1.y );
       return v2;
   }

   // Subtract vector v1 from v .....

   public Vector2D sub( Vector2D v1 ) {
       Vector2D v2 = new Vector2D( this.x - v1.x, this.y - v1.y );
       return v2;
   }

   // Scale vector by a constant ...

   public Vector2D scale( double scaleFactor ) {
       Vector2D v2 = new Vector2D( this.x*scaleFactor, this.y*scaleFactor );
       return v2;
   }

   // Normalize a vectors length....

   public Vector2D normalize() {
      Vector2D v2 = new Vector2D();

      double length = Math.sqrt( this.x*this.x + this.y*this.y );
      if (length != 0) {
        v2.x = this.x/length;
        v2.y = this.y/length;
      }

      return v2;
   }   

   // Dot product of two vectors .....

   public double dotProduct ( Vector2D v1 ) {
        return this.x*v1.x + this.y*v1.y;
   }
   
   public void truncate(double max)
   {
	   //System.out.println("longueur avant troncature " + this.length());
	   if(this.length() > max)
	   {
		   this.normalize();
		   this.scale(max);
	   }
   }
   
   public void Reinitialize()
   {
	   x = y = 0.0;
   }
   
   public double getX()
   {
	   return this.x;
   }

   public double getY()
   {
	   return this.y;
   }

}
