import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MemoryGame extends JFrame implements ActionListener {

    JButton[] cards = new JButton[16];
    int[] values = {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8};

    int first = -1;
    int second = -1;

    int score = 0;

    JLabel scoreLabel;
    JButton restartButton;

    public MemoryGame(){

        setTitle("Memory Matching Game");
        setSize(450,500);
        setLayout(new BorderLayout());

        shuffle(values);

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(4,4,5,5));

        for(int i=0;i<16;i++){

            cards[i] = new JButton("?");
            cards[i].setFont(new Font("Arial",Font.BOLD,22));
            cards[i].setBackground(Color.CYAN);
            cards[i].addActionListener(this);

            grid.add(cards[i]);
        }

        JPanel topPanel = new JPanel();

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial",Font.BOLD,18));

        restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());

        topPanel.add(scoreLabel);
        topPanel.add(restartButton);

        add(topPanel,BorderLayout.NORTH);
        add(grid,BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void shuffle(int[] arr){

        Random r = new Random();

        for(int i=0;i<arr.length;i++){
            int j = r.nextInt(arr.length);

            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public void actionPerformed(ActionEvent e){

        for(int i=0;i<16;i++){

            if(e.getSource()==cards[i]){

                cards[i].setText(String.valueOf(values[i]));
                cards[i].setBackground(Color.YELLOW);

                if(first==-1){
                    first=i;
                }
                else if(second==-1 && i!=first){
                    second=i;
                    checkMatch();
                }
            }
        }
    }

    public void checkMatch(){

        if(values[first]==values[second]){

            cards[first].setBackground(Color.GREEN);
            cards[second].setBackground(Color.GREEN);

            cards[first].setEnabled(false);
            cards[second].setEnabled(false);

            score++;
            scoreLabel.setText("Score: "+score);

            first=-1;
            second=-1;

            if(score==8){
                JOptionPane.showMessageDialog(this,"You Win!");
            }
        }

        else{

            javax.swing.Timer t = new javax.swing.Timer(700,new ActionListener(){

                public void actionPerformed(ActionEvent e){

                    cards[first].setText("?");
                    cards[second].setText("?");

                    cards[first].setBackground(Color.CYAN);
                    cards[second].setBackground(Color.CYAN);

                    first=-1;
                    second=-1;
                }
            });

            t.setRepeats(false);
            t.start();
        }
    }

    public void restartGame(){

        shuffle(values);

        score = 0;
        scoreLabel.setText("Score: 0");

        first = -1;
        second = -1;

        for(int i=0;i<16;i++){
            cards[i].setText("?");
            cards[i].setEnabled(true);
            cards[i].setBackground(Color.CYAN);
        }
    }

    public static void main
            (String[] args){
        new MemoryGame();
    }
}