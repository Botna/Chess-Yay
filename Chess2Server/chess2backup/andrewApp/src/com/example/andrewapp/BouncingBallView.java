package com.example.andrewapp;

import android.view.View;
import android.widget.ImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;



public class BouncingBallView extends View {
	private int xMin = 0;          // This view's bounds
	   private int xMax;
	   private int yMin = 0;
	   private int yMax;
	   private float ballRadius = 80; // Ball's radius
	   private float ballX = ballRadius + 20;  // Ball's center (x,y)
	   private float ballY = ballRadius + 40;
	   private float ballSpeedX = 5;  // Ball's speed (x,y)
	   private float ballSpeedY = 3;
	   private RectF ballBounds;      // Needed for Canvas.drawOval
	   private Paint paint;           // The paint (e.g. style, color) used for drawing
	   private Bitmap pic;
	   // Constructor
	   public BouncingBallView(Context context) {
	      super(context);
	      ballBounds = new RectF();
	      paint = new Paint();
	      //pic = BitmapFactory.decodeResource(getResources(), R.drawable.white_space);
	      
	      
	   }
	  
	   // Called back to draw the view. Also called by invalidate().
	   
	@Override
	   protected void onDraw(Canvas canvas) {
	      // Draw the ball
	      //ballBounds.set(ballX-ballRadius, ballY-ballRadius, ballX+ballRadius, ballY+ballRadius);
	    
	     // canvas.drawOval(ballBounds, paint);
	   
		canvas.drawBitmap(pic, 0.0f, 0.0f, paint);
		
	      //canvas.drawText("neil is super-hero gay", ballX, ballY, paint);
	      // Update the position of the ball, including collision detection and reaction.
	      update();
	  
	      // Delay
	      try {  
	         Thread.sleep(30);  
	      } catch (InterruptedException e) { }
	      
	      invalidate();  // Force a re-draw
	   }
	   
	   // Detect collision and update the position of the ball.
	   private void update() {
	      // Get new (x,y) position
	      ballX += ballSpeedX;
	      ballY += ballSpeedY;
	      // Detect collision and react
	      if (ballX  > xMax) {
	         ballSpeedX = -ballSpeedX;
	         ballX = xMax;
	      } else if (ballX  < xMin) {
	         ballSpeedX = -ballSpeedX;
	         ballX = xMin;
	      }
	      if (ballY  > yMax) {
	         ballSpeedY = -ballSpeedY;
	         ballY = yMax;
	      } else if (ballY < yMin) {
	         ballSpeedY = -ballSpeedY;
	         ballY = yMin;
	      }
	   }
	   
	   // Called back when the view is first created or its size changes.
	   @Override
	   public void onSizeChanged(int w, int h, int oldW, int oldH) {
	      // Set the movement bounds for the ball
	      xMax = w-1;
	      yMax = h-1;
	      
	      pic = Bitmap.createScaledBitmap(pic, xMax/2, yMax/2, true);
	   }
}
