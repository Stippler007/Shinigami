package klassen.npc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import klassen.Background;
import klassen.ImageFactory;
import klassen.karte.GameObjects;
import klassen.player.Player;

public abstract class NPC implements Serializable {

    protected String text;

    protected transient Player player;

    protected float x;
    protected float y;

    protected float speed;
    protected float speedX;
    protected float speedY;

    protected Rectangle bounding;
    protected transient BufferedImage look[][] = new BufferedImage[3][4];
    protected GameObjects[][] map;
    protected String imageTag;

    protected float animationTime;
    protected float maxAnimationTime = 0.9f;
    public boolean moving = true;

    public NPC(float x, float y, float speed,
            GameObjects[][] map, Player player, String text) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.bounding = new Rectangle((int) x, (int) y, 50, 50);
        this.player = player;
        this.map = map;
        this.text = text;
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        look = new BufferedImage[3][4];
        setLook(imageTag, 50, 50);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setMap(GameObjects[][] map) {
        this.map = map;
    }

    public void setPlayer(Player p) {
        player = p;
    }

    public Rectangle getBounding() {
        return bounding;
    }

    public void setLook(String imageName, int width, int height) {
        imageTag = imageName;
        System.out.println(imageName);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                look[i][j] = ImageFactory.getIF().getLook(imageTag).getSubimage(i * width, j * height, width, height);
            }
        }
        bounding.width = width;
        bounding.height = height;
    }

    public void update(float tslf) {
        if (animationTime < maxAnimationTime - tslf) {
            animationTime += tslf;
        } else {
            animationTime -= maxAnimationTime;
        }

        x += Player.speedX;
        y += Player.speedY;

        speedX *= tslf;
        speedY *= tslf;

        if (moving) {
            x += speedX;
        }
        if (moving) {
            y += speedY;
        }

        collideMap(tslf);

        bounding.x = (int) x;
        bounding.y = (int) y;
    }

    public void collideMap(float tslf) {
        for (int i = (int) Math.abs((x * -1 + Background.x) / 25) - 1; i < (int) Math.abs((x * -1 + Background.x) / 25) + 4; i++) {
            for (int j = (int) Math.abs((y * -1 + Background.y) / 25) - 1; j < (int) Math.abs((y * -1 + Background.y) / 25) + 4; j++) {
                if (!(i < 0 || j < 0) && !(i > map.length - 1 || j > map[0].length - 1)) {
                    Rectangle help1 = new Rectangle(bounding.x + (int) (speedX), bounding.y + (int) (speedY), bounding.width, bounding.height);
                    if (map[i][j].isSolid() && help1.intersects(map[i][j].getBounding())) {

                        Rectangle help2 = map[i][j].getBounding();

                        double vonlinks = x + help1.width - help2.x;
                        double vonoben = y + help1.height - help2.y;
                        double vonrechts = help2.x + help2.width - x;
                        double vonunten = help2.y + help2.height - y;

                        if (vonlinks < vonoben && vonlinks < vonrechts && vonlinks < vonunten) {
                            x -= vonlinks;
                        } else if (vonoben < vonrechts && vonoben < vonunten) {
                            y -= vonoben;
                        } else if (vonrechts < vonunten) {
                            x += vonrechts;
                        } else {
                            y += vonunten;
                        }
                    }
                    if (map[i][j].getBounding().intersects(bounding.x + bounding.width / 2, bounding.y + bounding.height / 2, 2, 1)) {
                        map[i][j].steppedOn(true);
                    }
                }
            }
        }
    }
    private float knockbackX = 0;
    private float knockbackY = 0;
    private float backKnockback = 300f;

    public void followPlayer() {
        speedX = (player.getBounding().x + player.getBounding().width / 2) - (x + bounding.width / 2);
        speedY = (player.getBounding().y + player.getBounding().height / 2) - (y + bounding.height / 2);

        float help = (float) Math.sqrt(speedX * speedX + speedY * speedY);

        speedX /= help;
        speedY /= help;

        speedX *= speed;
        speedY *= speed;
    }

    public void moveCockBack(float tslf) {
        if (knockbackX != 0) {
            if (knockbackX > 0) {
                knockbackX -= backKnockback * tslf;
                if (knockbackX < 0) {
                    knockbackX = 0;
                }
            } else {
                knockbackX += backKnockback * tslf;
                if (knockbackX > 0) {
                    knockbackX = 0;
                }
            }
        }
        if (knockbackY != 0) {
            if (knockbackY < 0) {
                knockbackY += backKnockback * tslf;
                if (knockbackY > 0) {
                    knockbackY = 0;
                }
            } else {
                knockbackY -= backKnockback * tslf;
                if (knockbackY < 0) {
                    knockbackY = 0;
                }
            }
        }
        x += knockbackX * tslf;
        y += knockbackY * tslf;
    }

    public void draw(Graphics2D g) {
        g.drawImage(getLook(), (int) x, (int) y, null);
    }

    public double getTurn() {
        double a = (player.getBounding().x + player.getBounding().width / 2) - (bounding.x + bounding.width / 2);
        double b = (player.getBounding().y + player.getBounding().height / 2) - (bounding.y + bounding.height / 2);

        double turn = Math.atan(b / a);
        if (a < 0) {
            turn += 2.3561944901923;
        }
        return turn;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String[] getText() {
        return text.split("\n");
    }

    public BufferedImage getLook() {
        if (player == null) {
            return look[0][0];
        }

        int j = -1;
        double turn = getTurn();
        if (turn >= -Math.PI * 0.25 && turn <= Math.PI * 0.25) {
            j = 1;
        } else if (turn >= Math.PI * 0.25 && turn <= Math.PI * 0.5) {
            j = 0;
        } else if (turn >= Math.PI * 0.50 && turn <= Math.PI * 1) {
            j = 2;
        } else {
            j = 3;
        }
        if (moving && speedX != 0 || speedY != 0) {
            for (int i = 0; i < look.length; i++) {
                if (animationTime < (float) maxAnimationTime / look.length * (i + 1)) {
                    return look[i][j];
                }
            }
        } else if (j != -1) {
            return look[0][j];
        }
        System.out.println("No image found! " + j);
        return look[0][0];
    }
}
