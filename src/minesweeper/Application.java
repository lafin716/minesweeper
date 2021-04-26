package minesweeper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 지뢰찾기 맵 생성 클래스
 * @author pjw
 * @since 2021.04.26
 * @version 1.0
 */
public class Application {
	
	// 가로 길이
	public final int ROW;
	
	// 세로 길이
	public final int COL;
	
	// 지뢰 갯수
	public final int MINE_COUNT;
	
	// 지뢰 
	public final String DELIM = "_"; 
	
	// 지뢰찾기 맵 배열
	public int[][] mineMap;
	
	// 지뢰 위치 정보 
	public Set<String> minePositions;
		
	// 생성자에서 기본값 세팅
	public Application() {
		ROW = 10;
		COL = 10;
		MINE_COUNT = 10;
	}
	
	// 맵 초기화
	public void createInitMap() {
		// 맵의 크기 만큼 지뢰 찾기 배열을 초기화
		mineMap = new int[ROW][COL];
		
		// 모든 사각형에는 숫자가 있어야 하므로 모든 배열에 값을 0으로 설정
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				mineMap[i][j] = 0;
			}
		}
	}
	
	// 지뢰 생성하고 지뢰 위치 고유값을 리턴
	public String generateMine() {
		
		// x축 랜덤
		int randomX = (int) (Math.random() * ROW);
		
		// y축 랜덤
		int randomY = (int) (Math.random() * COL);
		
		// 고유번호를 리턴
		return randomX + DELIM + randomY;		
	}
	
	// 지뢰 위치를 생성
	public void createMines() {
		
		// 중복을 피하기위해 HashSet을 이용하여 위치정보 초기화
		minePositions = new HashSet<String>();
		
		// 위치정보가 지뢰 갯수만큼 들어갈때 까지 반복
		while( minePositions.size() < MINE_COUNT ) {
			minePositions.add(generateMine());
		}
		
		// 지뢰맵에 생성한 지뢰 적용
		setMines();
	}
	
	// 위치정보를 지뢰맵에 -1 값으로 설정
	public void setMines() {
		
		// 위치정보 크기만큼 반복 
		Iterator<String> itor = minePositions.iterator();
		while (itor.hasNext()) {		
			
			// 위치정보를 파싱하여 위치정보 가져옴
			String[] position = itor.next().toString().split(DELIM);
			int x = Integer.parseInt(position[0]);
			int y = Integer.parseInt(position[1]);
			
		    // 해당 위치의 셀 값을 -1로 설정한다.
			mineMap[x][y] = -1;
			
			// 지뢰 주변의 셀들에 숫자를 1씩 증가하여 주변 지뢰갯수 세팅
			calculateCells(x, y);
		}
	}
	
	
	// 지뢰 주변 셀에 지뢰 갯수 증가
	public void calculateCells(int x, int y) {
		
		// x축의 시작값, 끝값을 정의
		int startX = x > 0 ? x - 1 : 0;
		int endX = x < ROW - 1 ? x + 1 : ROW - 1;
		
		// y축의 시작값, 끝값을 정의
		int startY = y > 0 ? y - 1 : 0;
		int endY = y < ROW - 1 ? y + 1 : ROW - 1;
		
		// 계산된 값을 범위로 반복
		for(int i = startX; i <= endX; i++) {
			for(int j = startY; j <= endY; j++) {
				// 지뢰 셀은 생략
				if(mineMap[i][j] == -1) continue;
				
				// 현재 지뢰 주변의 값을 증가
				mineMap[i][j]++;
			}
		}
	}
	
	// 지뢰 찾기 맵 생성
	public void create() {
		
		// 맵과 지뢰 정보를 초기화
		createInitMap();
		
		// 지뢰 생성 및 지뢰 갯수 셀 세팅
		createMines();
	}
	
	// 생성 된 맵 출력
	public void print() {
		
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				// x, y 형식에 맞게 출력하기 위해 j, i 순으로 출력
				int cellValue = mineMap[j][i];
				String cellText = cellValue == -1 ? "X" : Integer.toString(cellValue); 
				
				System.out.print("[" + cellText + "]");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		app.create();
		app.print();
	}
}
