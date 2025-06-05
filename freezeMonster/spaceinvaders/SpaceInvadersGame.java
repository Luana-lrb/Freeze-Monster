/**
* UNIVERSIDADE FEDERAL DE UBERLANDIA - UFU
 * PRINCÍPIOS E PADRÕES DE PROJETOS - 07/05/2025
 * Daniel Borges Gonçalves - 12311BCC005
 * Luana Rodrigues Borges - 12311BCC028
 * */

package spaceinvaders;

import java.awt.EventQueue;

import spriteframework.AbstractBoard;
import spriteframework.Commons;
import spriteframework.MainFrame;

public class SpaceInvadersGame extends MainFrame {
	private static final Commons config = SpaceCommons.getNewInstance();


	public SpaceInvadersGame () {
		super("Space Invaders", config);
	}
	
	protected  AbstractBoard createBoard() {
		return new SpaceInvadersBoard(config);
	}


	public static void main(String[] args) {

		EventQueue.invokeLater(SpaceInvadersGame::new);
	}

}


