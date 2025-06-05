/**
 * UNIVERSIDADE FEDERAL DE UBERLANDIA - UFU
 * PRINCÍPIOS E PADRÕES DE PROJETOS - 07/05/2025
 * Daniel Borges Gonçalves - 12311BCC005
 * Luana Rodrigues Borges - 12311BCC028
 * */

package freezeMonster;

import java.awt.EventQueue;
import spriteframework.Commons;
import spriteframework.AbstractBoard;
import spriteframework.MainFrame;

public class   FreezeMonsterGame extends MainFrame {
	private static final Commons config = EngineCommons.getNewInstance();

	public FreezeMonsterGame() {
		super("Freeze Monster", config);
	}
	
	protected AbstractBoard createBoard() {
		return new FreezeMonsterBoard(config);
	}
 
	public static void main(String[] args) {

		EventQueue.invokeLater(FreezeMonsterGame::new);
	}
}
