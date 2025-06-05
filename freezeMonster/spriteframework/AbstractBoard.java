package spriteframework;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import spriteframework.sprite.BadSprite;
import spriteframework.sprite.Player;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public abstract class AbstractBoard extends JPanel {

    protected Dimension d;
    
    protected LinkedList<Player> players;
    
    protected LinkedList<BadSprite> badSprites;
    protected Commons config;

    protected Image backgroundImage;
    private int numberPlayers;  // to do - future use
    protected boolean inGame = true;
    protected String message = "Game Over";

    protected Clip clip;

    protected Timer timer;


    // HotSpots
    protected abstract void createBadSprites(Commons config);
    protected abstract void createOtherSprites();
    protected abstract void drawOtherSprites(Graphics g);
    protected abstract void update();
    protected abstract void processOtherSprites(Player player, KeyEvent e);
    protected abstract void setPlayerMovementStrategy(Player player);
    protected abstract Player createPlayer(Commons Config);


    public AbstractBoard(Commons config) {
        this.config = config;
        initBoard();
        createPlayers();
		        numberPlayers = 1;
		        badSprites = new LinkedList<BadSprite>();
		        createBadSprites(config);
		        createOtherSprites();
                setBackgroundImage();

		//        shot = new Shot();
    }

    private void setBackgroundImage(){
        backgroundImage = new ImageIcon(getClass().getResource(config.getBackgroungPath())).getImage();

    }

    private void initBoard() {

    	addKeyListener(new TAdapter());
    	setFocusable(true);

    	d = new Dimension(config.getBoardWidth(),config.getBoardHeight());
    	setBackground(Color.black);

    	timer = new Timer(Commons.DELAY, new GameCycle());
    	timer.start();

    	createPlayers();
    	numberPlayers = 1;
    	badSprites = new LinkedList<BadSprite>();
    	createBadSprites(config);
    	createOtherSprites();
        playBackgroundMusic();


    }


    protected void createPlayers() {
		players = new LinkedList<Player>();
        players.add(createPlayer(config));
        for(Player player: players){
            setPlayerMovementStrategy(player);
        }
	}

   public Player getPlayer(int i) {
	   if (i >=0 && i<players.size())
		   return players.get(i);
	   return null;
   }
   
    private void drawBadSprites(Graphics g) {

        for (BadSprite bad : badSprites) {

            if (bad.isVisible()) {

                g.drawImage(bad.getImage(), bad.getX(), bad.getY(), this);
            }

            if (bad.isDying()) {

                bad.die();
            }

            if (bad.getBadnesses()!= null) {
            	for (BadSprite badness: bad.getBadnesses()) {
            		if (!badness.isDestroyed()) {
            			g.drawImage(badness.getImage(), badness.getX(), badness.getY(), this);
            		}
            	}
            }
        }
    }

    private void drawPlayers(Graphics g) {
    	for (Player player: players) {
    		if (player.isVisible()) {
    			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
    		}

    		if (player.isDying()) {

    			player.die();
    			inGame = false;
    		}
    	}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }


    private void playBackgroundMusic() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                    getClass().getResource(config.getBackgroungMusic()));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Música em loop
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDrawing(Graphics g1) { // Template Method
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (backgroundImage == null ){
            g.setColor(Color.black);
            g.fillRect(0, 0, d.width, d.height);
        } else {
            g.drawImage(backgroundImage, 0, 0, d.width, d.height, this);
        }

        g.setColor(config.getFinalLineColor());

        if (inGame) {

            g.drawLine(0, config.getGround(),
                   config.getBoardWidth(), config.getGround() );

            drawBadSprites(g);
            drawPlayers(g);
            drawOtherSprites(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(new Color(0,0,0,100));
        g.fillRect(0, 0, config.getBoardWidth(),config.getBoardHeight());

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, config.getBoardWidth() / 2 - 30, config.getBoardWidth() - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, config.getBoardWidth() / 2 - 30, config.getBoardWidth() - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (config.getBoardWidth() - fontMetrics.stringWidth(message)) / 2,
                config.getBoardWidth() / 2);
    }


    private void doGameCycle() {

        update();
        repaint();
    }


	private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            for (Player player: players)
                 player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	for (Player player: players) {
                player.keyPressed(e);

                processOtherSprites(player, e); // hotspot
        	}
        }
    }
}
