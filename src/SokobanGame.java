import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.awt.Point;

/**
 * @author 310
 */
public class SokobanGame {

    /**
     * 上一次的地图
     */
    private int[][] preMap;
    private int repeal = 0;
    /**
     * 当前的地图
     */
    private int[][] curMap;
    /**
     * 当前等级的地图
     */
    private int[][] curLevel;
    /**
     * 关卡数
     */
    private int iCurLevel = 0;
    /**
     * 移动了多少次
     */
    private int moveTimes = 0;
    private final int maxSteps = 100;

    private static final int SPACE = 0;
    private static final int WALL = 1;
    private static final int GOAL = 2;
    private static final int BOX = 3;
    private static final int PLAYER = 4;
    private static final int TRAP = 5;
    private static final Map<Integer, String> MAP = Map.of(
            SPACE, "\uD83C\uDF3F",
            WALL, "\uD83E\uDDF1",
            GOAL, "⭐",
            BOX, "\uD83D\uDCE6",
            PLAYER, "\uD83D\uDE42",
            TRAP, "⛔️");


    private char action;
    Scanner scanner = new Scanner(System.in);
    private static int[][][] levels = {{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 3, 0, 3, 2, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 2, 0, 3, 4, 1, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 3, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},

            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 4, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 3, 3, 1, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 3, 0, 1, 0, 1, 2, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 2, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},


            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 1, 3, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 4, 0, 3, 0, 0, 3, 0, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 2, 2, 1, 0, 3, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 1, 2, 2, 1, 0, 0, 0, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},


            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 4, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 3, 0, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 3, 0, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 2, 3, 0, 0, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 2, 2, 0, 2, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},


            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 4, 0, 1, 1, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 3, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 2, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 2, 3, 0, 0, 1, 0, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 2, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},


            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 2, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 3, 0, 3, 1, 2, 0, 1, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0}, {0, 1, 0, 2, 1, 3, 0, 3, 0, 1, 0, 1, 0, 0, 0, 0}, {0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0}, {0, 0, 1, 0, 1, 1, 1, 2, 0, 0, 0, 0, 4, 1, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0}, {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}};

    private Point prePosition;

    /**
     * 初始化游戏
     */
    private void init() {
        initLevel();//初始化对应等级的游戏
        drawMap();//绘制出当前等级的地图
        showMoveInfo();//初始化对应等级的游戏数据
    }

    //初始化游戏等级

    /**
     * 初始化游戏等级
     */

    private int[][] copyArray(int[][] motoArray) {
        int row = motoArray.length;
        int column = motoArray[0].length;
        int[][] res = new int[row][column];

        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++) {
                res[i][j] = motoArray[i][j];
            }

        return res;
    }

    private void initLevel() {

        //当前移动过的游戏地图
        curMap = copyArray(levels[iCurLevel]);
        //当前等级的初始地图
        curLevel = levels[iCurLevel];

        //寻找角色初始位置
        for (int i = 0; i < curLevel.length; i++) {
            for (int j = 0; j < curLevel[0].length; j++) {
                if (curLevel[i][j] == PLAYER) {
                    prePosition = new Point(i, j);
                    break;
                }
            }
        }
        //游戏关卡移动步数清零
        moveTimes = 0;
    }

    private void selectLevel(int level) {
        //iCurLevel当前的地图关数
        iCurLevel = level;
        int len = levels.length;
        if (iCurLevel < 0 || iCurLevel > len - 1) {
            System.out.println("关卡范围为1~" + len);
            return;
        }
        //初始当前等级关卡
        init();
    }

    /**
     * 小人移动
     *
     * @param dir 移动方向
     */
    private void go(String dir) {
        preMap = copyArray(curMap);

        Point p1 = null, p2 = null;
        switch (dir) {
            case "up":
                //获取小人前面的两个坐标位置来进行判断小人是否能够移动
                p1 = new Point(prePosition.x - 1, prePosition.y);
                p2 = new Point(prePosition.x - 2, prePosition.y);
                break;
            case "down":
                p1 = new Point(prePosition.x + 1, prePosition.y);
                p2 = new Point(prePosition.x + 2, prePosition.y);
                break;
            case "left":
                p1 = new Point(prePosition.x, prePosition.y - 1);
                p2 = new Point(prePosition.x, prePosition.y - 2);
                break;
            case "right":
                p1 = new Point(prePosition.x, prePosition.y + 1);
                p2 = new Point(prePosition.x, prePosition.y + 2);
                break;
            default:
                break;
        }
        //若果小人能够移动的话，更新游戏数据，并重绘地图
        if (tryGo(p1, p2)) {
            moveTimes++;
            repeal = 1;

            //重绘当前更新了数据的地图
            drawMap();
            showMoveInfo();

            //若果移动完成了进入下一关
            if (checkFinish()) {
                System.out.println("恭喜过关！！");
                selectLevel(iCurLevel + 1);
            }
            if (moveTimes == maxSteps) {
                System.out.println("ゲームオーバー");
                selectLevel(iCurLevel);
            }
        }
    }

    private void drawMap() {
        for (int i = 0; i < curMap.length; i++) {
            for (int j = 0; j < curMap[0].length; j++) {
                System.out.print(MAP.get(curMap[i][j]) + "  ");
            }
            System.out.println();
        }
    }

    //repeal

    private void repeal() {
        if (repeal != 0) {
            repeal = 0;
            moveTimes--;
            curMap = copyArray(preMap);
            drawMap();
            showMoveInfo();
        } else
            System.out.println("当前不能进行撤回操作！");
    }

    private void showMoveInfo() {
        System.out.println("当前关卡：" + iCurLevel);
        System.out.println("当前步数：" + moveTimes);
        System.out.println("1：围墙   2：目标点   3：箱子    4：人物");
    }

    //判断是否推成功

    /**
     * 判断是否推成功
     *
     * @return true 推成功 false 推失败
     */
    private boolean checkFinish() {
        for (int i = 0; i < curMap.length; i++) {
            for (int j = 0; j < curMap[i].length; j++) {
                //当前移动过的地图和初始地图进行比较，若果初始地图上的陷进参数在移动之后不是箱子的话就指代没推成功
                if (curLevel[i][j] == GOAL && curMap[i][j] != BOX) {
                    return false;
                }
            }
        }
        return true;
    }

    //判断小人是否能够移动

    /**
     * 判断小人是否能够移动
     *
     * @param p1 小人前面的坐标
     * @param p2 小人前面的前面的坐标
     * @return true 能够移动 false 不能移动
     */
    private boolean tryGo(Point p1, Point p2) {
        if (p1.x < 0 || p1.y < 0 || p1.x > curMap.length || p1.y > curMap[0].length || curMap[p1.x][p1.y] == WALL) {
            return false;
        }
        //若果小人前面是箱子那就还需要判断箱子前面有没有障碍物(箱子/墙)
        if (curMap[p1.x][p1.y] == BOX) {
            if (curMap[p2.x][p2.y] == WALL || curMap[p2.x][p2.y] == BOX) {
                return false;
            }


            //若果判断不成功小人前面的箱子前进一步
            //更改地图对应坐标点的值
            curMap[p2.x][p2.y] = BOX;

        }
        //若果都没判断成功小人前进一步
        //更改地图对应坐标点的值
        curMap[p1.x][p1.y] = PLAYER;
        //若果小人前进了一步，小人原来的位置如何显示
        int v = curLevel[prePosition.x][prePosition.y];
        //若果刚开始小人位置不是陷进的话
        if (v != GOAL) {
            //可能是5 既有箱子又陷进

            //小人移开之后之前小人的位置改为地板
            v = SPACE;
        }
        //重置小人位置的地图参数
        curMap[prePosition.x][prePosition.y] = v;
        //若果判断小人前进了一步，更新坐标值
        prePosition = p1;
        //若果小动了 返回true 指代能够移动小人
        return true;
    }
    //判断是否推成功


    private void getInput() {
        System.out.print("请输入一个字符：");
        // 读取字符
        action = scanner.next().charAt(0);
    }

    private void getLevelFromInput() {

        System.out.print("请输入要跳转的关卡：");
        // 读取字符
        int toLevel = scanner.nextInt();
        selectLevel(toLevel - 1);
    }

    private void execute() {
        switch (action) {
            case 'a':
                go("left");
                break;
            case 'w':
                go("up");
                break;
            case 'd':
                go("right");
                break;
            case 's':
                go("down");
                break;
            case 'r':
                repeal();
                break;
            case 'e':
                getLevelFromInput();
                break;
            default:
                break;
        }
    }


    public static void main(String[] args) {
        SokobanGame sokobanGame = new SokobanGame();

        sokobanGame.init();
        while (true) {
            sokobanGame.getInput();
            sokobanGame.execute();
        }
    }
}