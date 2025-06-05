package spriteframework;

import javax.swing.JFrame;

public abstract class MainFrame extends JFrame  {

    // hotspot
    protected abstract AbstractBoard createBoard();
    
    public MainFrame(String t, Commons config) {
          
        add(createBoard());
		
		setTitle(t);
		setSize(config.getBoardWidth(), config.getBoardHeight());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
    }
    
}
