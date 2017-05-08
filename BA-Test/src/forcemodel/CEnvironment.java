package forcemodel;

import java.awt.*;
import javax.swing.JPanel;
import javax.vecmath.Vector2d;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
//import java.util.Random;
import java.util.stream.IntStream;


public class CEnvironment extends JPanel {

    private Graphics2D graphics2d;
    //private Random rand = new Random();
    //private static final double m_GaussianMean = 1.34;
    //private static final double m_GaussianStandardDeviation = 0.26;
    private ArrayList<CPedestrian> m_pedestrian = new ArrayList<CPedestrian>();
    private ArrayList<CStatic> m_wall = new ArrayList<>( 2 );
    private ArrayList<CWall> m_walledge = new ArrayList<>( );
    public ArrayList<COutput> test = new ArrayList<>();

    public CEnvironment() {

        setFocusable( true );
        setBackground( Color.WHITE );
        setDoubleBuffered( true );


        //first test

        m_wall.add( new CStatic( 100, 0, 20, 820 ) );
        //m_wall.add( new CStatic( 600, 190, 182, 150 ) );



        m_wall.forEach( i->
        {
            m_walledge.add(i.getwall1());
            m_walledge.add(i.getwall2());
            m_walledge.add(i.getwall3());
            m_walledge.add(i.getwall4());
        }
        );



        //first test 0 + ( Math.random() * 100 ), 100 + ( Math.random() * 50 )

        //Vector2D(30,20):Falsch 90,50 ok; Vector 2D Pos: 80,60, Radius 10
        
        IntStream.range( 0, 1 )
                .forEach( i -> m_pedestrian.add( new CPedestrian( new Vector2d(36,40),
                		1, new CGoal(415, 250, 0, 100).get_goals(), this ) ) );

        IntStream.range( 0, 1 )
                .forEach( i -> m_pedestrian.add( new CPedestrian( new Vector2d( 36,0 ),
                		1, new CGoal(310, 350, 550, 200).get_goals(), this ) ) );

      

    }

    /**
     * paint all elements
     * @return
     **/
    public void paint( Graphics g ) {
        super.paint( g );
        graphics2d = ( Graphics2D ) g;
        drawWall(Color.GRAY);
        drawPedestrian();
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     * draw each static element wall
     * @return
     **/
    private void drawWall(Color color) {
        graphics2d.setColor( color ) ;
        m_wall.forEach( i -> graphics2d.fillRect( i.getX1(), i.getY1(), i.getWidth(), i.getHeight() ) );
    }

    /**
     * draw each pedestrian
     * @return
     **/
    private void drawPedestrian() {
        graphics2d.setColor( Color.BLUE ) ;
        for( int j = 0; j < 1; j++ )
        {
            Ellipse2D.Double shape = new Ellipse2D.Double( m_pedestrian.get(j).getPosition().getX(), m_pedestrian.get(j).getPosition().getY(), 30, 30 ); //60
            graphics2d.fill( shape );
        }
        graphics2d.setColor( Color.ORANGE ) ;
        for( int j = 1; j < 2; j++ )
        {
            Ellipse2D.Double shape = new Ellipse2D.Double( m_pedestrian.get(j).getPosition().getX(), m_pedestrian.get(j).getPosition().getY(), 30, 30 );
            graphics2d.fill( shape );
        }
        /*
        m_pedestrian.stream()
                .forEach(i -> {
                    Ellipse2D.Double shape = new Ellipse2D.Double( i.getPosition().getX(), i.getPosition().getY(), 10, 10 );
                    graphics2d.fill( shape );

                });*/

    }
    /**
     * get the list of pedestrian with their information
     * @return a list of pedestrian information
     **/
    public ArrayList<CPedestrian> getPedestrianinfo()
    {
        return m_pedestrian;
    }

    /**
     * get the list of walls with their information
     * @return a list of wall information
     **/
    public ArrayList<CWall> getWallinfo()
    {
        return m_walledge;
    }

    /**
     * undate environment state
     * @return
     **/
    public void update() {

            m_pedestrian.stream()
                    .parallel()
                    .forEach( j ->
                    {
                        try
                        {
                            j.call();
                        }
                        catch ( final Exception l_exception ) {}
                    });
    }
}
