package model;

import java.util.Random;

/* This class must extend Game */
public class ClearCellGame extends Game {

	Random randomObject;
	int strategy;
	int currentScore;

	//Constructor. I call the super class to create board. Then I instantiate
	//my class variables and set all cells to empty.
	public ClearCellGame(int maxRows, int maxCols, java.util.Random random,
			int strategy) {
		super(maxRows, maxCols);
		//Calls super class constructor to create board.
		randomObject = random;
		this.strategy = strategy;
		for(int i = 0; i<maxRows; i++){
			for(int j = 0; j<maxCols; j++){
				//Set all cells to empty.
				super.setBoardCell(i, j, BoardCell.EMPTY);
			}
		}
	}

	@Override
	//I determine if the game has ended by simply checking if the the last row
	//is not all empty.
	public boolean isGameOver() {
		//Game is over when the last row is different than empty.
		int maxRows = super.getMaxRows();
		int maxCols = super.getMaxCols();
		for(int j = 0; j<maxCols; j++){
			//BoardCell current = board[maxRows - 1][j];
			//If one of the cells in the last row is not empty, game is over.
			if(!(this.getBoardCell(maxRows - 1,j) == (BoardCell.EMPTY))){
				return true;
			}
		}
		//All cells in the last rows are empty, game is not over.
		return false;
	}

	@Override
	//I return the score, which is a class variable that accounts for the points
	//earned in the game.
	public int getScore() {
		return currentScore;

	}

	@Override
	//If the game is not over, I push the rows down by one and I set 
	//a new random row at the top.
	public void nextAnimationStep() {
		//If the game is not over, it executes the next animation.
		if(!isGameOver()){
			//First I push the rows down by one. i is rows and j is columns.
			//I start from the second to last row, and from there I push each
			//row down.
			int maxCols = super.getMaxCols();
			for(int i = board.length-2; i>=0; i--){
				for(int j = 0; j<maxCols; j++){
					super.setBoardCell(i+1,  j, board[i][j]);
				}
			}
			for(int j = 0; j<maxCols; j++){
				//I set the new random row at the the top.
				setBoardCell(0, j,
						BoardCell.getNonEmptyRandomBoardCell(randomObject));
			}
		}

	}

	@Override
	//I check the cells diagonally and adjacent to the cell passed in,
	//I use increments to continue to check the next cells if they are 
	//the same color. I account for a row being empty and collapsing, in which
	//case I push all the rows up one.
	public void processCell(int rowIndex, int colIndex) {
		int maxCols = super.getMaxCols();
		int maxRows = super.getMaxRows();
		//BoardCell processedCell = this.getBoardCell(rowIndex, colIndex);
		//BoardCell processedCell = board[rowIndex][colIndex];

		if(this.getBoardCell(rowIndex,colIndex) == (BoardCell.EMPTY)){
			return;
		}

		//Diagonal Cells
		//Top left, top right, bottom left, bottom right.
		//First I check if that position from the processedCell exists, and 
		//if it does exist, I check if the cell has the same color as the 
		//processed cell. If it fits both requirements, I set it to empty.
		//I also add to the score when a cell is cleared.
		
		//Top left
		int m = 1;
		if(rowIndex>0){
			for(int i = colIndex-1; i>-1; i--){
				if((rowIndex-m)>=0 && getBoardCell(rowIndex-m, i) == 
						(this.getBoardCell(rowIndex,colIndex))){
					setBoardCell(rowIndex - m, i, BoardCell.EMPTY);
					currentScore++;
					m++;
				} else{
					break;
				}
			}
		}
//		if((colIndex-1 >= 0) && (rowIndex-1)>=0 && 
//				(this.getBoardCell(rowIndex-1,colIndex-1)
//						== (this.getBoardCell(rowIndex,colIndex)))){
//			//Top left from cell
//			setBoardCell(rowIndex-1, colIndex-1, BoardCell.EMPTY);
//			currentScore++;
//		}
		
		//Top Right
		int k = 1;
		if(rowIndex>0){
			for(int i = colIndex+1; i<maxCols; i++){
				if((rowIndex-k)>=0 && getBoardCell(rowIndex-k, i) == 
						(this.getBoardCell(rowIndex,colIndex))){
					setBoardCell(rowIndex-k, i, BoardCell.EMPTY);
					currentScore++;
					k++;
				} else{
					break;
				}
			}
		}
//		if((colIndex+1 <= maxCols-1) && (rowIndex-1)>=0 && 
//				(this.getBoardCell(rowIndex-1,colIndex+1)
//						== (this.getBoardCell(rowIndex,colIndex)))){
//			//Top right from the cell
//			setBoardCell(rowIndex-1, colIndex+1, BoardCell.EMPTY);
//			currentScore++;
//		}
		
		//Bottom Left
		int n = 1;
		if(rowIndex>0){
			for(int i = colIndex-1; i>-1; i--){
				if((rowIndex+n)<=(maxRows-1) && getBoardCell(rowIndex+n, i) == 
						(this.getBoardCell(rowIndex,colIndex))){
					setBoardCell(rowIndex + n, i, BoardCell.EMPTY);
					currentScore++;
					n++;
				} else{
					break;
				}
			}
		}
//		if((colIndex-1 >= 0) && (rowIndex+1)<=(maxRows-1) && 
//				(this.getBoardCell(rowIndex+1,colIndex-1)
//						== (this.getBoardCell(rowIndex,colIndex)))){
//			//Bottom left from the cell
//			setBoardCell(rowIndex+1, colIndex-1, BoardCell.EMPTY);
//			currentScore++;
//		}
		
		//Bottom right
		int l = 1;
		if(rowIndex>0){
			for(int i = colIndex+1; i<maxCols; i++){
				if((rowIndex+l)<=(maxRows-1) && getBoardCell(rowIndex+l, i) == 
						(this.getBoardCell(rowIndex,colIndex))){
					setBoardCell(rowIndex+l, i, BoardCell.EMPTY);
					currentScore++;
					l++;
				} else{
					break;
				}
			}
		}
//		if((colIndex+1 <= maxCols-1) && (rowIndex+1)<=(maxRows-1) && 
//				(this.getBoardCell(rowIndex+1,colIndex+1)
//						== (this.getBoardCell(rowIndex,colIndex)))){
//			//Bottom right from cell
//			setBoardCell(rowIndex+1,colIndex+1, BoardCell.EMPTY);
//			currentScore++;
//		}



		//Adjacent Cells
		//Right, Left, Bottom, Top.
		//First I check if that position from the processedCell exists, and 
		//if it does exist, I check if the cell has the same color as the
		//processed cell. If it fits both requirements, I set it to empty.
		//I also add to the score when a cell is cleared.
		
		//Right cell
		if(rowIndex>0){
			for(int i = colIndex+1; i<maxCols; i++){
				if(getBoardCell(rowIndex, i) == 
						(this.getBoardCell(rowIndex,colIndex))){
					setBoardCell(rowIndex, i, BoardCell.EMPTY);
					currentScore++;
				} else{
					break;
				}
			}
		}
		//		int counter =1;
		//		if(((colIndex+1) <= (maxCols-1)) && 
		//				(this.getBoardCell(rowIndex,colIndex+1)
		//						== (this.getBoardCell(rowIndex,colIndex)))){
		//			//Right
		//			setBoardCell(rowIndex,colIndex+1, BoardCell.EMPTY);
		//			currentScore++;
		//			while(((colIndex+1) <= (maxCols-1)) && 
		//					(this.getBoardCell(rowIndex,colIndex+1+counter)
		//							== (this.getBoardCell(rowIndex,colIndex)))){
		//				setBoardCell(rowIndex,colIndex+1+counter, BoardCell.EMPTY);
		//				currentScore++;
		//				counter++;
		//			}
		//		}
		
		//Left cell
		if(rowIndex>0){
			for(int i = colIndex-1; i>-1; i--){
				if(getBoardCell(rowIndex, i) == 
						(this.getBoardCell(rowIndex,colIndex))){
					setBoardCell(rowIndex, i, BoardCell.EMPTY);
					currentScore++;
				} else{
					break;
				}
			}
		}

		//		counter =1;
		//		if(((colIndex-1) >= 0) && 
		//				(this.getBoardCell(rowIndex,colIndex-1)
		//						== (this.getBoardCell(rowIndex,colIndex)))){
		//			//Left
		//			setBoardCell(rowIndex,colIndex-1, BoardCell.EMPTY);
		//			currentScore++;
		//			while(((colIndex-1) >= 0) &&
		//					(this.getBoardCell(rowIndex,colIndex-1-counter)
		//							== (this.getBoardCell(rowIndex,colIndex)))){
		//				setBoardCell(rowIndex,colIndex-1-counter, BoardCell.EMPTY);
		//				currentScore++;
		//				counter++;
		//			}
		//		}

		//Bottom cell
		if(rowIndex>0){
			for(int i = rowIndex+1; i<maxRows; i++){
				if(getBoardCell(i, colIndex) == 
						(this.getBoardCell(rowIndex,colIndex))){
					setBoardCell(i, colIndex, BoardCell.EMPTY);
					currentScore++;
				} else{
					break;
				}
			}
		}
		//		counter =1;
		//		if(((rowIndex+1) <= (maxRows-1)) && 
		//				(this.getBoardCell(rowIndex+1,colIndex)
		//						== (this.getBoardCell(rowIndex,colIndex)))){
		//			//Bottom
		//			setBoardCell(rowIndex+1,colIndex, BoardCell.EMPTY);
		//			currentScore++;
		//			while(((rowIndex+1) <= (maxRows-1)) && 
		//					(this.getBoardCell(rowIndex+1+counter,colIndex)
		//							== (this.getBoardCell(rowIndex,colIndex)))){
		//				setBoardCell(rowIndex+1+counter,colIndex, BoardCell.EMPTY);
		//				currentScore++;
		//				counter++;
		//			}
		//		}

		//Top cell
		if(rowIndex>0){
			for(int i = rowIndex - 1; i>-1; i--){
				if(getBoardCell(i, colIndex) == 
						(this.getBoardCell(rowIndex,colIndex))){
					setBoardCell(i, colIndex, BoardCell.EMPTY);
					currentScore++;
				} else{
					break;
				}
			}
		}

		//		counter =1;
		//		if(((rowIndex-1) >= 0) && 
		//				(this.getBoardCell(rowIndex-1,colIndex)
		//						== (this.getBoardCell(rowIndex,colIndex)))){
		//			//Top
		//			setBoardCell(rowIndex-1,colIndex, BoardCell.EMPTY);
		//			currentScore++;
		//			while(((rowIndex-1) >= 0) && 
		//					(this.getBoardCell(rowIndex-1-counter,colIndex)
		//							== (this.getBoardCell(rowIndex,colIndex)))){
		//				setBoardCell(rowIndex-1-counter,colIndex, BoardCell.EMPTY);
		//				currentScore++;
		//				counter++;
		//			}
		//		}


		//Now that we set all the cells around the current cell that has the 
		//same color as it to empty, we can set the actual cell to empty.
		//Add one to score.
		setBoardCell(rowIndex,colIndex, BoardCell.EMPTY);
		currentScore++;

		//Now I check to see if the row at rowIndex is empty.
		int emptyCounter = 0;
		for(int j = 0; j<=maxCols - 1; j++){
			if(this.getBoardCell(rowIndex,j) == (BoardCell.EMPTY)){
				emptyCounter++;
			}
		}
		//If the emptyCounter == maxCols, rowIndex is empty and collapses.
		if(emptyCounter == maxCols){
			//The row is empty, move the row below it up. We don't look at the 
			//last row because there is nothing after it.
			for(int i = rowIndex; i<=maxRows-2; i++){
				for(int j = 0; j<=maxCols-1; j++){
					setBoardCell(i,j, this.getBoardCell(i+1,j));
				}
			}
			//Now that we moved all the rows up one to account for the 
			//collapsed row, we must set the last row to empty.
			for(int j = 0; j<=maxCols-1; j++){
				setBoardCell(maxRows-1,j, BoardCell.EMPTY);
			}
		}

	}


}