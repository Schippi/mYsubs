package tool;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.ImageObserver;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.naming.ServiceUnavailableException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;

import org.bridj.Pointer;
import org.bridj.cpp.com.COMRuntime;
import org.bridj.cpp.com.shell.ITaskbarList3;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionListResponse;
import com.google.common.collect.Lists;
import com.google.protobuf.ServiceException;

import gui.StartProgressBar;
import gui.StartProgressBarUI;
import gui.TableHeaderRenderer;

//https://google-developers.appspot.com/youtube/v3/docs/

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements ClipboardOwner {
	private static final boolean PORT_ENABLED = true;

	public static final Color redColor = new Color(199, 16, 26);
	public static final Color evenColor = new Color(230, 230, 230);
	public static final Color evennewColor = new Color(180, 230, 180);
	public static final Color oddnewColor = new Color(205, 255, 205);

	private static final int DATAAMOUNT_MAX = 500;
	private static final int DATAAMOUNT_STEP = 5;
	private static final int INIT_DELAY = 500;
	private static final long LONG_TIME = 20000;
	// ///
	public static final String AUTH_PROGRAM_NAME = "mYsubs";
	public static final String VERSION_NUMBER = "0.12";
	private static final String FULL_PROGRAM_NAME = AUTH_PROGRAM_NAME + " v." + VERSION_NUMBER;
	private static final String FILE_NAME_DATA = AUTH_PROGRAM_NAME + ".data";
	private static final String FILE_NAME_LOG = AUTH_PROGRAM_NAME + ".log";
	// ///
	private static int PORT = 56813;
	private static final int PORT_DEFAULT = PORT;
	private static final int PORT_MAX = 65534;
	private static final String LOCAL_HOST = "127.0.0.1";

	public static final int TAB_TABLE_INDEX = 0;
	public static final int TAB_FAVETABLE_INDEX = 1;
	public static final int TAB_SETTINGS_INDEX = 2;
	public static final int TAB_HELP_INDEX = 3;

	public static final int TABLE_DATUM_COL_INDEX = 0;
	public static final int TABLE_AUTHOR_COL_INDEX = 1;
	public static final int TABLE_TITLE_COL_INDEX = 2;
	public static final int TABLE_URL_COL_INDEX = 3;

	private static final String SOUND_FILE_NEW = "data/sounds/new.wav";
	private static final String SOUND_FILE_START = "data/sounds/start.wav";
	private static final long SOUND_FILE_START_TIME = 1000;
	private static final String SOUND_FILE_END = "data/sounds/end.wav";
	private static final long SOUND_FILE_END_TIME = 700;

	private static final int ICON_SIZE_SMALL = 16;
	private static final String ICON = "data/icons/";
	private static final String ICON_FILE = ICON + "FrameIcon.png";
	private static final String ICON_FILE_TRAY = ICON + "TrayIcon.png";
	private static final String ICON_FILE_OPTIONS = ICON + "preferences.png";
	private static final String ICON_FILE_DELETE = ICON + "delete.png";
	private static final String ICON_FILE_FAVE = ICON + "favoritelib.png";
	private static final String ICON_FILE_FAVEADD = ICON + "favorite.png";
	private static final String ICON_FILE_UPDATE = ICON + "update.png";
	private static final String ICON_FILE_HELP = ICON + "help.png";
	private static final String ICON_FILE_SEARCH = ICON + "search.png";
	private static final String ICON_FILE_SEARCH_KILL = ICON + "searchkill.png";
	private static final String ICON_FILE_COPY = ICON + "copy.png";
	private static final String ICON_FILE_TOTOP = ICON + "toTop.png";
	private static final String ICON_FILE_WARNING = ICON + "warning.png";
	private static final String ICON_FILE_SAVE = ICON + "save.png";
	private static final String ICON_FILE_AUDIO_ON = ICON + "audio_on.png";
	private static final String ICON_FILE_AUDIO_OFF = ICON + "audio_off.png";

	private static final String ICON_FILE_THUMBNAIL = ICON + "defaultThumbnail.png";
	private static final String ICON_FILE_THUMBNAIL_LOAD = ICON + "loadThumbnail.png";
	private static final String ICON_FILE_VIEWED = ICON + "viewed.png";
	private static final String ICON_FILE_PLAY = ICON + "play.png";

	private static final String TUBE_URL_PRE_ID = "https://www.youtube.com/watch?v=";
	public static final int TUBE_URL_PRE_ID_LENGTH = TUBE_URL_PRE_ID.length();

	private static final String TOOLTIP_AUTOUPDATE = "if enabled searches for new videos every " + 60 + " minutes";
	private static final String TOOLTIP_BACKTOTOP = "puts " + AUTH_PROGRAM_NAME
			+ " to the front again after opening a video";
	private static final String TOOLTIP_DATAAMOUNT = "0 = all Entries";
	private static final String TOOLTIP_ONLYNEW = "if checked only new Entries which match the search will be shown";
	private static final String TOOLTIP_PORT = "port used to ensure only one instance of " + AUTH_PROGRAM_NAME
			+ " is present (restart required) 0 = disable feature, default: " + PORT_DEFAULT;
	private static final String TOOLTIP_PURGE = "Deletes all TableData when confirmed";
	private static final String TOOLTIP_PURGE_CONFIRM = "Confirm that you want to delete all Tabledata";
	private static final String TOOLTIP_SEARCH = "Search";
	private static final String TOOLTIP_SOUNDS = "play  start- / new videos- / end- Sounds";
	private static final String TOOLTIP_STARTMINIMIZED = "starts " + AUTH_PROGRAM_NAME + " minimized";
	private static final String TOOLTIP_STARTUPUPDATE = "when checked " + AUTH_PROGRAM_NAME + " will update on startup";
	private static final String TOOLTIP_TOTOP = "Scrolls the Table to the top";
	private static final String TOOLTIP_TRAY = "enables / disables the TrayIcon in your Taskbar";
	private static final String TOOLTIP_TRAY_UNAVAILABLE = "Tray feature not available on your System";
	private static final String TOOLTIP_TRAY_NOTE = "Notify when error happen or new videos are available";
	private static final String TOOLTIP_UPDATE = "search if new videos are available";
	private static final String TOOLTIP_MULTI = "uses multiple threads to update (uses more bandwidth, but significantly increases updatespeed)";
	private static final String TOOLTIP_REVERSE = "if checked playlists will start with the lowest selected video";

	private static final String ERROR_ALREADY_RUNNING = AUTH_PROGRAM_NAME + " is already running";

	private static final String ERROR_CORRUPTDATA = "Data File was corrupt - created new - update?";
	private static final String ERROR_LOCALDATA_IO = "IO error -> admin / restart / install again / delete "
			+ FILE_NAME_DATA;
	static final String ERROR_LOGGED = "Error: see logfile for details";
	private static final String ERROR_NO_RIGHTS = "\nno rights to write logfile on disk or something";
	private static final String ERROR_NOT_SQUARE = "wrong resolution of Icon";
	private static final String ERROR_PORT_INVALID = "Bad Port, choose a different one";
	private static final String ERROR_SOUND = "Soundfiles not found or corrupt";
	private static final String ERROR_UPDATE = "update failed: ";
	private static final String ERROR_UPDATE_URL = ERROR_UPDATE + "URL Error - update program";
	private static final String ERROR_UPDATE_IO = ERROR_UPDATE + "IO Error";
	private static final String ERROR_UNKNOWN = "unknown Error, please send logfile and describe what you did";
	private static final String ERROR_PLAYLIST = "error while creating playlist";

	private static final String BOOLEAN_STRING_TRUE = "true";

	static final String TEXT_PERCENT = "%";
	static final String TEXT_NOTHING = "";
	static final String TEXT_SPACE = " ";
	static final String TEXT_LINEFEED = "\n";

	private static final String TEXT_HTML = "text/html";
	private static final String TEXT_REPLACE = "\\p{Cntrl}";
	static final String TEXT_TRIPLELINEWRAP = "\n\n\n";

	static final String TEXT_TRAY_UPDATE_RUNNING = "Update: ";
	static final String TEXT_TRAY_NEWVID_SINGLE = " new video";
	static final String TEXT_TRAY_NEWVID_MULTI = TEXT_TRAY_NEWVID_SINGLE + "s";
	static final String TEXT_TRAY_SINCE = " at ";
	private static final String TEXT_PORT = "Port:    ";
	private static final String TEXT_UPDATE_RUNNING = "updating...";
	private static final String TEXT_SAVING_LONG = "saving took very long, consider purging entries";
	private static final String TEXT_LOADING_LONG = "loading took very long, consider purging entries";
	private static final String TEXT_OPTIONS = "Preferences";
	private static final String TEXT_UPDATE = "update";
	private static final String TEXT_TUBENAME = " Youtube Username:";
	private static final String TEXT_TRAY_NOTE = " Tray Notification ";
	private static final String TEXT_TRAY = " Tray Icon ";
	private static final String TEXT_SOUNDS = "Sounds     ";
	private static final String TEXT_STARTUPUPDATE = "start update on startup";
	private static final String TEXT_AUTO_UPDATE = "auto update ";
	private static final String TEXT_BACKTOTOP = "Jump back after click";
	private static final String[] TEXT_DATAAMOUNT = { " Show newest", "    Entries" };
	private static final String TEXT_NEW = "new: ";
	private static final String TEXT_PURGE = "Purge Data";
	private static final String TEXT_PURGE_CONFIRM = "are you sure?";
	private static final String TEXT_SAVE = "Save";
	private static final String TEXT_EXIT = "Exit";
	private static final String TEXT_OPEN = "Open";
	private static final String TEXT_RESETCOUNTER = "reset counter";
	private static final String TEXT_STARTMINIMIZED = "start minimized";
	private static final String TEXT_ONLYNEW = "only new";
	private static final String TEXT_REVERSE = "reverse Playlists";

	private static final String[] LOGGER = { "\n~~~~~~~" + FULL_PROGRAM_NAME + "~~~~~~~\n",
			"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n" };

	private static final String HELP_ABOUT = "<h2>About:</h2>" + FULL_PROGRAM_NAME + "\t release 20190504<br/>"
			+ "\tauthor: <i>Carsten \"Schippi\" Schipmann</i><br/>";

	private static final String HELP_HELP = "<h2>Help</h2>"
			+ "enter your YouTube Username, and your Login-Data in the preferences<br/>"
			+ "if you dont have your own channel yet, you need to create it<br/>"
			+ "note that i dont know if saving the password is safe - but my guess is that its not<br/>"
			+ "the table will update the 50 newest entrys from everyone you are subscribed to.<br/>"
			+ "subscriptions of 'Show's' are not supported yet<br/>" + "autoupdate does update every hour<br/>"
			+ "rightclick entries to delete them. if misclicked purge data is required<br/>"
			+ "click the ID to open the video in your Browser<br/>"
			+ "Tray options only work if your system supports that<br/>"
			+ "it is recommended that you purge the database every so often to decrease loading time<br/><br/>"
			+ "<b>please send feedback, suggestions and bugreports to <font color=#0000FF>theschippi@googlemail.com</font></b><br/>";

	private static final String HELP_CHANGELOG_04 = "<h3>0.4</h3>" + "Yes... column sizes adjusted<br/>"
			+ "on top is now a search is available<br/>" + "under options: limit amount of data shown<br/>"
			+ "under options: start minimized<br/>" + "portlistening implemented to ensure singlestart<br/>"
			+ "toTop button on to right implemented ( scrolls table back up to the top)<br/>"
			+ "back to front option added<br/>";

	private static final String HELP_CHANGELOG_05 = "<h3>0.5</h3>" + "now with less typos typos<br/>"
			+ "and improved trayfunctionality<br/>" + "General: option to only show new entries added<br/>"
			+ "implemented Windows 7 TaskBar-progress support<br/>" + "variable to update on startup added<br/>"
			+ "easily doubleclick author to search him<br/>";

	private static final String HELP_CHANGELOG_06 = "<h3>0.6</h3>"
			+ "volatile spelling errors fixed and couple of minor bugs<br/>"
			+ "enabled multirowselection in table and added right-click menu to copy URL's of selected videos to export<br/>"
			+ "remade searching<br/>" + "General: standard is now to only show 50 entries<br/>"
			+ "on Win7: fixed some taskbar progressbar stuff<br/>" + "new: clear search button added<br/>";

	private static final String HELP_CHANGELOG_07 = "<h3>0.7</h3>" + "new GUI<br/>"
			+ "added ability to add local favorites<br/>" + "fixed delete<br/>" + "delete now in context menu<br/>"
			+ "New: implemented Thumbnailpreview - visit options to activate<br/>"
			+ "endless code cleanup ( ok only a bit) <br/>";

	private static final String HELP_CHANGELOG_08 = "<h3>0.8</h3>" + "increased updatespeed<br/>"
			+ "fixed jumpback after click<br/>" + "fixed a bug with deleted accounts<br/>" + "improved search<br/>"
			+ "gui improvements<br/>" + "<ul><li>better tooltips</li>" + "<li>fixed helpsection</li>"
			+ "<li>better optionsmenu</li>" + "<li>improved thumbnail viewed indication</li></ul>";

	private static final String HELP_CHANGELOG_09 = "<h3>0.9</h3>" + "fixed critical bug with multithreading<br/>"
			+ "severely improved multithreading speed<br/>" + "improved logging and debugging<br/>"
			+ "added commandline parameter --nosave to prevent saving additional data<br/>"
			+ "fixed rightclick menu when searchfilter was active<br/>" + "made some errors clearer<br/>"
			+ "improved layout for linux users<br/>" + "TaskBarProgress should now work on Windows 8<br/>"
			+ "added a startup Loading Bar<br/>"
			+ "fixed a bug which would allow multiple instances even if the port feature was enabled<br/>";

	private static final String HELP_CHANGELOG_10 = "<h3>0.10</h3>" + "fixed deletion on favorite tab<br/>"
			+ "new feature: create a playlist from all selected videos.<br/> " + "<ul>"
			+ "<li> rightclick after selecting multiple videos and press 'Play'.</li>"
			+ "<li> option to reverse the order of videos in created playlists</li>" + "</ul>"
			+ "introducing all sorts of keybinds" + "<ul>" + "<li>Ctrl+1 or (2,3,4): navigate tabs</li>"
			+ "<li>F1: help</li>" + "<li>F2: search for author of first selected video</li>"
			+ "<li>F3: jump to searchbar</li>" + "<li>F5: update</li>"
			+ "<li>Enter (while navigating a table): play video"
			+ "<li>Ctrl+Shift+1: toggle Thumbnails on subscribtion tab</li>"
			+ "<li>Ctrl+Shift+2: toggle Thumbnails on favorites tab</li>"
			+ "<li>Ctrl+Enter: display the duration of the selected videos (only works with videos added with version 0.10 or later</li>"
			+ "<li>(WIP) Ctrl+Shift+Enter: display some stats</li>"
			+ "<li>while on subscribtions or favorites just start typing to search</li>" + "</ul>";
	
	private static final String HELP_CHANGELOG_11 = "<h3>0.11</h3>" + "OAuth";
	private static final String HELP_CHANGELOG_12 = "<h3>0.12</h3>" + "Youtube APIv3";

	private static final String HELP_CHANGELOG_FULL = "<h2>change log</h2>" + HELP_CHANGELOG_12+ HELP_CHANGELOG_11+HELP_CHANGELOG_10 + HELP_CHANGELOG_09
			+ HELP_CHANGELOG_08 + HELP_CHANGELOG_07 + HELP_CHANGELOG_06 + HELP_CHANGELOG_05 + HELP_CHANGELOG_04;

	private static final String HELP_KNOWN_BUGS = "<h2>known bugs: </h2>"
			+ "<b> none of these bugs break the program</b><br/>"
			+ "sometimes throws exception when exiting the program<br/>"
			+ "not really tested on other platforms other than windows<br/>"
			+ "update on startup sometimes doesn't work (maybe fixed) must restart<br/>";

	private static final String HELP = HELP_ABOUT + "\n" + HELP_HELP + "\n" + HELP_CHANGELOG_FULL + "\n"
			+ HELP_KNOWN_BUGS;

	private static final String LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	private static final Dimension MINIMUM_SIZE = new Dimension(640, 330);

	private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	private static final int COUNT_PER_THREAD = 10;

	private static final int FONTSIZE = 12;

	private static final String[] PLAYLIST_CREATING = { "creating playlist..", "creating playlist...." };

	private static final String POP_PLAY = "Play";
	private static final String POP_COPY = "copy URL's to clipboard";
	private static final String POP_VIEWED = "mark as viewed";
	private static final String POP_TOFAVE = "add to favorites";
	private static final String POP_DELETE = "delete";

	private static final String POP_INFOCOPY = "copy text";

	private static JPopupMenu pop = new JPopupMenu();
	private static JPopupMenu favepop = new JPopupMenu();
	private static JPopupMenu infopop = new JPopupMenu();

	private boolean nosave = false;

	JSpinner showData = new JSpinner(new SpinnerNumberModel(0, 0, DATAAMOUNT_MAX, DATAAMOUNT_STEP));
	public TestModel model = new TestModel();
	public JCheckBox autoupdate, playSounds, wantTray, trayNotification, startminimized,
			backToFront = new JCheckBox(), startupupdate, showOnlyNew, showThumb, showfaveThumb, multi, reversePlaylist;
	private boolean trayavailable = false;
	ServerThread serverthread;
	JFrame frame;
	volatile JButton update;
	Thread updateThread;
	Clip sound_new, sound_start, sound_end;
	CountingTrayIcon trayicon;
	PrintStream logger;
	int exceptioncnt = 0;
	JProgressBar updateProgress;
	static MainPanel thisMainPanel;
	JPanel bot;
	JButton debugbtn;
	JTextField tubename;
	MenuItem updateMenuItem;
	JButton purgeData, purgeConfirm;
	TableRowSorter<TestModel> sorter;
	JTextField searchField = new JTextField();
	JTextField portField;
	JTable table;
	volatile int cnt = 0;
	int old_data_amount = 0;
	public Image defaultImg;
	public static Image loadImg;
	private URLRenderer urlrenderer;
	private ImageRenderer imgrenderer;
	JTabbedPane tabbo;
	JLabel info;

	ITaskbarList3 list;
	Pointer<Integer> hwnd;
	boolean onWin7 = false;
	boolean newstuff = false;

	private int[] win7Progress = new int[2];

	private TestModel faveModel = new TestModel();
	private VideoTable faveTable = new VideoTable(faveModel);;

	private TableRowSorter<TestModel> faveSorter;

	private JButton toTop;

	private Container glassPane;

	private GridBagConstraints gbc;
	public static Stats stats = new Stats();
	VideoGrid vidgr;

	private boolean newVersion = true;

	private JEditorPane helpPane;

	private Point initialClick = new Point(0, 0);

	private JPanel gpcont;

	private static int debug;

	private static boolean noload = false;

	private static YouTube youtube;

	private static boolean fetchall = false;

	/**
	 * MenuIcons from http://openiconlibrary.sourceforge.net/gallery2/
	 */
	public MainPanel(boolean nosave2) {
		super(new BorderLayout());
		nosave = nosave2;
		thisMainPanel = this;
		initlogger();
		try {
			init();
			start_local_server();
		} catch (Exception e) {
			log(e);
		}

	}

	private void initlogger() {
		try {
			logger = new PrintStream(new FileOutputStream(new File(FILE_NAME_LOG), true));
		} catch (FileNotFoundException e2) {
			try {
				logger = new PrintStream(new FileOutputStream(new File(FILE_NAME_LOG)));
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(ERROR_NO_RIGHTS);
				System.exit(-1);
			}
		}

	}

	class ServerThread extends Thread {
		ServerSocket server;

		@Override
		public void run() {
			try {
				server = new ServerSocket(PORT);

				while (true) {
					try {
						server.accept();
						if (debug != 0) {
							frame.setVisible(true);
							frame.setState(Frame.NORMAL);
						} else {
							System.exit(0);
						}
					} catch (IOException e) {
						if (e instanceof SocketException) {
							break;
						}
						frame.setVisible(true);
						frame.setState(Frame.NORMAL);
					}
				}
			} catch (IOException e1) {
				if (e1 instanceof BindException) {
					log(new Exception(ERROR_PORT_INVALID));
					System.exit(0);
				} else {
					log(e1);
				}
			}
		}
	}

	private void start_local_server() {
		serverthread = new ServerThread();
		serverthread.start();
	}

	private StartProgressBar initLoadBar() {
		// System.out.println(UIManager.put("ProgressBar.foreground",
		// Color.blue));
		// System.out.println(UIManager.put("ProgressBar.foreground",
		// Color.blue));
		// UIManager.put("ProgressBar.foreground", Color.blue);
		// UIManager.put("ProgressBar.selectionBackground", Color.red);
		// UIManager.put("ProgressBar.selectionForeground", Color.green);

		JFrame loadi = new JFrame(AUTH_PROGRAM_NAME);
		// if(debug==0){
		// loadi.setIconImage(getIcon(ICON_FILE_DELETE).getImage());
		// }else{
		loadi.setIconImage(getIcon(ICON_FILE).getImage());
		// }

		loadi.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		JPanel imgPanel = new ImgPanel();
		// loadi.setExtendedState(JFrame.MAXIMIZED_BOTH);
		loadi.setUndecorated(true);
		StartProgressBar loadibar = new StartProgressBar(loadi);
		loadibar.setUI(new StartProgressBarUI());
		loadibar.setBorderPainted(false);
		loadibar.setMaximum(90);
		loadi.setLayout(new BorderLayout());
		loadi.add(imgPanel, BorderLayout.CENTER);
		loadi.add(loadibar, BorderLayout.SOUTH);
		loadibar.setValue(0);
		loadibar.setPreferredSize(new Dimension(150, 30));
		loadi.setSize(150, 170);
		// loadi.setLocation(1750, 0);
		loadi.setLocationRelativeTo(null);
		loadibar.setValue(40);
		loadi.setVisible(true);

		return loadibar;
	}

	private void init() {

		StartProgressBar loadibar = initLoadBar();

		updateThread = new UpdateThread();
		frame = new JFrame(AUTH_PROGRAM_NAME);

		// frame.setContentPane(new JPanel() {
		// int MARGIN = 50;
		//
		// @Override
		// public void paintComponent(Graphics g) {
		//
		// ((Graphics2D) g).setRenderingHint(
		// RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		//
		//
		// super.paintComponent(g);
		//// g.setColor(Color.blue);
		//// ((Graphics2D) g).setStroke(new BasicStroke(3));
		// g.fillRoundRect(-1, -1, this.getWidth()/2, this.getHeight()/2, 29,
		// 29);
		// }
		//
		// });
		// ((JPanel)frame.getContentPane()).setBorder(BorderFactory.createLineBorder(new
		// Color(117,39,39), 3));

		// ((JPanel)frame.getContentPane()).setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		// frame.setBorder(BorderFactory.createStrokeBorder(new
		// BasicStroke(5)));
		ActionListener exitlisten = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exitthis();
			}
		};

		ActionListener optionlisten = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.fireTableDataChanged();
				faveModel.fireTableDataChanged();
				purgeConfirm.setEnabled(false);
				rightRenderer();
			}
		};
		ActionListener updatelisten = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
					fetchall = true;
				} else {
					fetchall = false;
				}
				performupdate();
			}
		};
		ActionListener searchlisten = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newFilter(searchField.getText());
				table.requestFocus();
			}
		};

		KeyListener searchKeyListen = new KeyAdapter() {

			private void toggleThumb(JCheckBox toT) {
				toT.setSelected(!toT.isSelected());
				model.fireTableDataChanged();
				faveModel.fireTableDataChanged();
				rightRenderer();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				boolean tablef = false;
				JTable currTable = null;
				int row = -1;
				boolean selRow = false;
				if ((table.hasFocus() || faveTable.hasFocus())) {
					tablef = true;
					currTable = getCurrentFocussedTable();
					row = currTable.getSelectedRow();
					if (row != -1) {
						selRow = true;
					}
				}
				if (e.isControlDown()) {
					if (e.getKeyCode() == KeyEvent.VK_F) {
						searchField.requestFocus();
					} else if (e.isShiftDown()) {
						if (e.getKeyCode() == KeyEvent.VK_1) {
							toggleThumb(showThumb);
						} else if (e.getKeyCode() == KeyEvent.VK_2) {
							toggleThumb(showfaveThumb);
						} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							info.setText(stats.toString());
							info.setToolTipText(info.getText());
						}
					} else if (e.getKeyChar() >= '1' && e.getKeyChar() <= '4') {
						tabbo.setSelectedIndex(e.getKeyChar() - '1');
					} else if (e.getKeyCode() == KeyEvent.VK_ENTER && tablef) {
						long secs = 0;
						URL maurl;
						myVidEntry v;
						for (int i = 0; i < currTable.getSelectedRows().length; i++) {
							try {
								maurl = new URL(currTable.getValueAt(currTable.getSelectedRows()[i], 3).toString());
								v = ((TestModel) currTable.getModel()).getDatabyURL(maurl);
								secs += v.getDuration();
							} catch (MalformedURLException e1) {
								log(e1);
							}
						}
						info.setText("Duration: " + formatSecs(secs));
						info.setToolTipText(info.getText());
					}

				} else if (e.getKeyCode() == KeyEvent.VK_F1) {
					tabbo.setSelectedIndex(3);
					((JScrollPane) tabbo.getComponentAt(3)).getVerticalScrollBar().setValue(0);
				} else if (e.getKeyCode() == KeyEvent.VK_F5) {
					performupdate();
				} else if (selRow && tablef && e.getKeyCode() == KeyEvent.VK_F2) {
					String s = currTable.getValueAt(currTable.getSelectedRow(), 1).toString();
					searchField.setText(s);
					newFilter(s);
				} else if (tablef && (e.getKeyCode() == KeyEvent.VK_SPACE)) {
					if (selRow && currTable.getSelectedColumn() == 3) {
						urlrenderer.mouse(currTable, currTable.getSelectedColumn(), currTable.getSelectedRow());
					}
					return;
				} else if (tablef && e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					if (selRow) {
						if (currTable.getSelectedRowCount() == 1) {
							urlrenderer.mouse(currTable, 3, row);
							currTable.setRowSelectionInterval(row, row);
						} else if (currTable.getSelectedRowCount() > 1) {
							createPlayList(currTable);
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
						|| e.getKeyCode() == KeyEvent.VK_SHIFT) {
					// is handled by table keylistener
				} else if (tablef) {
					searchField.requestFocus();
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						try {
							int len = searchField.getText().length();
							if (e.isControlDown()) {
								len = 0;
							}
							searchField.setText(len > 0 ? searchField.getText(0, len) : TEXT_NOTHING);
							searchField.setCaretPosition(searchField.getText().length());

						} catch (BadLocationException ve) {
							log(ve);
						}
						return;
					}
					char c = e.getKeyChar();
					if (isPrintable(c)) {
						String truncated = TEXT_NOTHING + c;
						truncated = truncated.replaceAll(TEXT_REPLACE, TEXT_NOTHING);
						searchField.setText(searchField.getText() + truncated);
						searchField.setCaretPosition(searchField.getText().length());
					}
				}

			}

			private String formatSecs(long secs) {
				long days = TimeUnit.SECONDS.toDays(secs);
				long hours = TimeUnit.SECONDS.toHours(secs) % 24;
				long minutes = TimeUnit.SECONDS.toMinutes(secs) % 60;
				if (days == 1) {
					return String.format("%d day %02d:%02d:%02d", days, hours, minutes, secs % 60);
				} else {
					return String.format("%d days %02d:%02d:%02d", days, hours, minutes, secs % 60);
				}

			}

		};

		defaultImg = new ImageIcon(getClass().getClassLoader().getResource(ICON_FILE_THUMBNAIL)).getImage();
		loadImg = new ImageIcon(getClass().getClassLoader().getResource(ICON_FILE_THUMBNAIL_LOAD)).getImage();
		prepareImage(defaultImg, null);
		// frame.setUndecorated(true);
		// frame.addComponentListener(new ComponentAdapter() {
		// // Give the window an elliptical shape.
		// // If the window is resized, the shape is recalculated here.
		// @Override
		// public void componentResized(ComponentEvent e) {
		// frame.setShape(new RoundRectangle2D.Double(0, 0, frame
		// .getWidth(), frame.getHeight(), 30, 30));
		// }
		// });

		// TODO
		frame.setLayout(new BorderLayout());
		add(initTable(searchKeyListen), BorderLayout.CENTER);
		frame.setSize(new Dimension(1000, 480));
		frame.setMinimumSize(MINIMUM_SIZE);
		loadibar.setValue(5);
		initmenu(searchlisten, searchKeyListen);
		loadibar.setValue(15);
		initTabs(optionlisten, searchKeyListen);
		loadibar.setValue(20);
		// initoptions(optionlisten);

		frame.add(tabbo, BorderLayout.CENTER);

		update = new JButton(TEXT_UPDATE) {
			@Override
			public JToolTip createToolTip() {
				MultiLineToolTip tip = new MultiLineToolTip();
				tip.setComponent(this);
				return tip;
			}

		};
		update.setName("update");
		try {
			ImageIcon upicon = new ImageIcon(getClass().getClassLoader().getResource(ICON_FILE_UPDATE));
			update.setIcon(upicon);
		} catch (NullPointerException e) {
			log(e);
		}
		update.setToolTipText(TOOLTIP_UPDATE);
		update.addActionListener(updatelisten);
		bot = new JPanel(new BorderLayout());
		bot.add(update, BorderLayout.SOUTH);

		if (System.getProperty("Debugging") != null) {
			debug = 0;
		} else {
			debug = 1;
		}

		if (debug == 0) {
			initdebug();
		}

		add(bot, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);

		frame.setPreferredSize(new Dimension(1000, 480));

		updateProgress = new JProgressBar();
		updateProgress.setString(TEXT_NOTHING);

		updateProgress.setStringPainted(true);
		updateProgress.setForeground(new Color(42, 42, 122));

		// updateProgress.setBackground(Color.YELLOW);
		// updateProgress.setForeground(Color.CYAN);
		// update.setUI(new BasicButtonUI());
		loadibar.setValue(30);
		readLocalData(loadibar);
		// loadibar = 85 here
		initSounds();
		initTray(exitlisten, optionlisten, updatelisten);
		loadibar.setValue(88);
		if (playSounds.isSelected()) {
			sound_start.start();
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(SOUND_FILE_START_TIME);
					} catch (InterruptedException e) {
						log(e);
					}
					sound_start.close();
				}
			}.start();
		}

		sortAllRowsBy(model, 0, false);
		loadibar.setValue(92);
		new AutoUpdateThread(this);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exitthis();
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				if (trayavailable && wantTray.isSelected()) {
					frame.setVisible(false);
					// trayicon.resetcnt();
				} else {
					frame.setState(Frame.ICONIFIED);
					// trayicon.resetcnt();
				}
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				if (onWin7 && updateThread.isAlive()) {
					if (updateProgress.isIndeterminate()) {
						restoreWinPB(ITaskbarList3.TbpFlag.TBPF_INDETERMINATE);
					} else {
						changeTaskBarProgressValue(win7Progress[0], win7Progress[1]);
					}
				}
			}

			@Override
			public void windowActivated(WindowEvent arg) {
				if (!updateThread.isAlive() && hwnd != null) {
					restoreWinPB(ITaskbarList3.TbpFlag.TBPF_NOPROGRESS);
				}
			}

		});
		if (debug == 0) {
			frame.setIconImage(getIcon(ICON_FILE_DELETE).getImage());
		} else {
			frame.setIconImage(getIcon(ICON_FILE).getImage());
		}
		frame.pack();
		setColumnSizes();
		rightRenderer();
		loadibar.setValue(96);
		Rectangle tabBounds = tabbo.getBoundsAt(0);
		// TODO
		glassPane = (Container) frame.getRootPane().getGlassPane();

		// GlassPaneListener gpListener = new GlassPaneListener (toTop, update,
		// frame);
		// glassPane.addMouseListener(gpListener);
		// this.addMouseListener(gpListener);
		// this.addMouseMotionListener(gpListener);
		// glassPane.addMouseWheelListener(gpListener);
		// //TODO

		glassPane.setVisible(true);
		glassPane.setLayout(new GridBagLayout());
		glassPane.setBackground(Color.red);
		gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.NONE;
		// gbc.insets = new Insets(2, 155, 2, 2);
		gbc.insets = new Insets(tabBounds.y + 27, 90, 2, 2);
		gbc.anchor = GridBagConstraints.NORTHEAST;
		toTop = new JButton(getIcon(ICON_FILE_TOTOP));
		toTop.setPreferredSize(new Dimension(25, 25));
		toTop.addKeyListener(searchKeyListen);
		toTop.setToolTipText(TOOLTIP_TOTOP);
		toTop.setBorderPainted(true);
		toTop.setBorder(BorderFactory.createEmptyBorder());
		toTop.setFocusPainted(false);
		// toTop.setContentAreaFilled(false);
		toTop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTable currTable = getCurrentVisibleTable();
				if (!(currTable.getParent() instanceof JViewport)) {
					return;
				}
				JViewport viewport = (JViewport) currTable.getParent();

				// This rectangle is relative to the table where the
				// northwest corner of cell (0,0) is always (0,0).
				Rectangle rect = currTable.getCellRect(0, 0, true);

				// The location of the viewport relative to the table
				Point pt = viewport.getViewPosition();

				// Translate the cell location so that it is relative
				// to the view, assuming the northwest corner of the
				// view is (0,0)
				rect.setLocation(rect.x - pt.x, rect.y - pt.y);

				// Scroll the area into view
				viewport.scrollRectToVisible(rect);
				currTable.requestFocus();
			}
		});
		gpcont = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 6; // 2 columns wide
		c.gridy = 0;
		JPanel dasdew = new JPanel() {
			private Image img = new ImageIcon(getClass().getClassLoader().getResource(ICON + "exitbar.png")).getImage();

			@Override
			public void paintComponent(Graphics g) {
				if (img != null) {
					// try {
					// m.waitForID(0, 300);
					// } catch (InterruptedException e) {
					// e.printStackTrace();
					// }
					g.drawImage(img, 1, 1, null);
				} else {
					g.drawImage(MainPanel.thisMainPanel.defaultImg, 1, 1, null);
				}
			}
		};
		dasdew.setPreferredSize(new Dimension(170, 25));
		gpcont.add(dasdew);

		/**
		 * c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 0.5; c.gridx = 0;
		 * c.gridwidth = 2; //2 columns wide c.gridy = 0; gpcont.add(new
		 * JButton(getIcon(ICON_FILE_DELETE)), c);
		 * 
		 * c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 0.5; c.gridwidth = 2; //2
		 * columns wide c.gridx = 2; c.gridy = 0; gpcont.add(new
		 * JButton(getIcon(ICON_FILE_DELETE)), c);
		 * 
		 * c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 0.5; c.gridwidth = 2; //2
		 * columns wide c.gridx = 4; c.gridy = 0; gpcont.add(new
		 * JButton(getIcon(ICON_FILE_DELETE)), c);
		 * 
		 */

		// gpcont.setOpaque(false);
		JButton cls = new JButton(getIcon(ICON_FILE_DELETE));
		cls.setContentAreaFilled(false);
		cls.setForeground(Color.red);
		// TODO setFocusPainted, setPressedIcon, setRolloverEnabled
		// closePanel.add();
		// closePanel.add(new JButton(getIcon(ICON_FILE_DELETE)));
		// closePanel.add(new JButton(getIcon(ICON_FILE_DELETE)));
		//
		// gpcont.add(closePanel);

		LayoutManager lay = new FlowLayout(FlowLayout.RIGHT);
		JPanel ttPanel = new JPanel(lay);
		ttPanel.setBackground(Color.RED);
		// ttPanel.setPreferredSize(new Dimension(120,27));
		ttPanel.setAlignmentY(0);
		toTop.setAlignmentY(2);
		// ttPanel.setOpaque(false);
		// ttPanel.add(new JButton());
		// ttPanel.add(new JButton());
		// ttPanel.add(new JButton());

		c.fill = GridBagConstraints.VERTICAL;
		c.ipady = 0; // reset to default
		c.weightx = 0.75;
		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.EAST; // bottom of space
		c.insets = new Insets(0, 0, 0, 0); // top padding
		c.gridx = 7; // aligned with button 2
		c.gridwidth = 1; // 2 columns wide
		c.gridy = 1; // third row
		gpcont.setOpaque(false);
		gpcont.add(toTop, c);

		// ttPanel.add(toTop);
		// gpcont.add(ttPanel);

		// gpcont.setPreferredSize(new Dimension(120 + 4 * 2, 50));
		glassPane.add(toTop, gbc);
		loadibar.killFrame();
		if (startminimized.isSelected()) {
			frame.setState(Frame.ICONIFIED);
			if (!(wantTray.isSelected() && trayavailable)) {
				try {
					Thread.sleep(INIT_DELAY);
				} catch (InterruptedException e1) {
				}
				frame.setVisible(true);
			}
		} else {
			try {
				Thread.sleep(INIT_DELAY);
			} catch (InterruptedException e1) {
			}
			frame.setVisible(true);
		}
		if (tubename.getText().length() == 0) {
			tabbo.setSelectedIndex(3);
			if (tubename.getText().length() == 0)
				tubename.requestFocus();
		} else if (newVersion) {
			tabbo.setSelectedIndex(3);
			JScrollPane jsp = ((JScrollPane) tabbo.getComponentAt(3));
			jsp.getVerticalScrollBar().setValue(310);
			helpPane.requestFocus();
		} else {
			table.requestFocusInWindow();
			table.requestFocus();
		}
		initWin7();
		if (startupupdate.isSelected()) {
			updateGo();
		}

	}

	protected void performupdate() {
		update.setEnabled(false);
		updateGo();
		trayicon.resetcnt();
	}

	private class TabsUI extends BasicTabbedPaneUI {

		@Override
		protected void installDefaults() {
			super.installDefaults();
			// lightHighlight = Color.red;

		}

		/**
		 * this function draws the border around each tab note that this function does
		 * now draw the background of the tab. that is done elsewhere
		 */
		@Override
		protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {
			g.setColor(lightHighlight);

			switch (tabPlacement) {
			case TOP:

				g.drawLine(x, y + 2, x, y + h - 1); // left highlight
				g.drawLine(x + 1, y + 1, x + 1, y + 1); // top-left highlight
				g.drawLine(x + 2, y, x + w - 3, y); // top highlight
				if (tabIndex != 5) {
					g.setColor(shadow);
					g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 1); // right
																		// shadow

					g.setColor(darkShadow);
					g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 1); // right
																		// dark-shadow
					g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1); // top-right
																	// shadow
				}
				break;
			default:
				super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);

			}
		}

		@Override
		protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
				Rectangle iconRect, Rectangle textRect, boolean isSelected) {

		}

		@Override
		protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
			int f = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
			return f;
		}

		@Override
		protected int calculateTabHeight(int a, int b, int c) {
			return 22;
		}

	}

	private void initTabs(ActionListener optionlisten, KeyListener searchKeyListen) {
		tabbo = new JTabbedPane();
		tabbo.setUI(new TabsUI());
		tabbo.setAlignmentX(5);
		tabbo.setAlignmentY(5);
		tabbo.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbo.addTab("", getIcon(ICON_FILE_TRAY), this);
		tabbo.addTab("", getIcon(ICON_FILE_FAVE), initTablefaves(searchKeyListen));

		JScrollPane sp = new JScrollPane(initoptions(optionlisten, searchKeyListen));

		sp.getVerticalScrollBar().setUnitIncrement(10);
		tabbo.addTab("", getIcon(ICON_FILE_OPTIONS), sp);

		helpPane = new JEditorPane();
		helpPane.setEditable(false);
		helpPane.setContentType(TEXT_HTML);
		helpPane.setFont(new Font(helpPane.getFont().getFontName(), Font.PLAIN, FONTSIZE));
		helpPane.setText(stringtoHTML(HELP));
		helpPane.addKeyListener(searchKeyListen);
		JScrollPane jsp = new JScrollPane(helpPane);

		jsp.addKeyListener(searchKeyListen);
		tabbo.addTab("", getIcon(ICON_FILE_HELP), jsp);

		JPanel yay = new JPanel();
		yay.setOpaque(false);
		JLabel s = new JLabel(getIcon(ICON_FILE_SEARCH));
		s.setOpaque(false);
		yay.add(s);
		yay.add(searchField);
		yay.add(showOnlyNew);
		JButton searchClearBtn = new JButton();
		searchClearBtn.setIcon(getIcon(ICON_FILE_SEARCH_KILL));
		searchClearBtn.setContentAreaFilled(false);
		searchClearBtn.setBorderPainted(false);
		searchClearBtn.setFocusPainted(false);
		searchClearBtn.setPreferredSize(new Dimension(16, 16));
		searchClearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchField.setText(TEXT_NOTHING);
				showOnlyNew.setSelected(false);
				newFilter(TEXT_NOTHING);
			}
		});
		yay.add(searchClearBtn);

		ChangeListener ml = new ChangeListener() {

			int last = 0;

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (tabbo.getSelectedIndex() == 4) {
					searchField.requestFocus();
					tabbo.setSelectedIndex(last);
				} else if (tabbo.getSelectedIndex() == 5) {
					tabbo.setSelectedIndex(last);
				} else if (last == 2 && newstuff) {
					newstuff = false;
					int po = Integer.valueOf(portField.getText());
					if (PORT != 0) {
						try {
							serverthread.server.close();
						} catch (IOException e) {
							log(e);
						}
					}

					if (po != PORT && po >= 0 && po < PORT_MAX) {
						PORT = po;
						if (po != 0) {
							serverthread = new ServerThread();
							serverthread.start();
						}
					}
					saveLocalData();
					if (!wantTray.isSelected() && !frame.isVisible()) {
						frame.setState(Frame.ICONIFIED);
						frame.setVisible(true);
					}
					rightRenderer();
					table.repaint();
				}
				if (tabbo.getSelectedIndex() != 0 && tabbo.getSelectedIndex() != 1) {
					glassPane.remove(toTop);
				} else {
					glassPane.add(toTop, gbc);
				}
				last = tabbo.getSelectedIndex();
			}
		};
		tabbo.addChangeListener(ml);
		tabbo.addTab("wubwubwub", new JLabel(""));
		tabbo.setTabComponentAt(4, yay);
		// tabbo.addTab("subs", initsubs());

		info = new JLabel(TEXT_NOTHING, SwingConstants.LEFT);
		info.setSize(new Dimension(200, 20));
		info.setMaximumSize(info.getSize());
		info.setPreferredSize(info.getSize());
		info.setFocusable(false);
		info.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					infopop.show(info, arg0.getX() + 10, arg0.getY());
				}
			}

		});
		GlassPaneListener gpListener = new GlassPaneListener(toTop, update, frame, info);
		tabbo.addMouseListener(gpListener);
		tabbo.addMouseMotionListener(gpListener);
		info.addMouseListener(gpListener);
		info.addMouseMotionListener(gpListener);
		// info.setBorder(BorderFactory.createEmptyBorder());

		tabbo.addTab("wubwubwub", new JLabel(""));
		tabbo.setTabComponentAt(5, info);

	}

	public Component initsubs() {
		SubsModel m = new SubsModel();
		JTable o = new JTable(m);
		m.store("1");
		m.store("2");
		m.store("3");
		m.store("4");
		return o;
	}

	private Component initTablefaves(KeyListener searchKeyListen) {
		faveSorter = new TableRowSorter<TestModel>(faveModel);
		faveTable.setRowSorter(faveSorter);
		faveTable.setColumnSelectionAllowed(false);
		faveTable.setRowSelectionAllowed(true);
		faveTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		faveTable.setIntercellSpacing(new Dimension());
		faveTable.setShowGrid(false);
		faveTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		faveTable.setAutoCreateRowSorter(true);
		faveTable.setDefaultRenderer(URL.class, urlrenderer);
		faveTable.setDefaultRenderer(char.class, new CenterRenderer());
		faveTable.setDefaultRenderer(String.class, new LineWrapRenderer());

		faveTable.addMouseListener(urlrenderer);
		faveTable.addMouseMotionListener(urlrenderer);
		faveTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					if (faveTable.getSelectedRowCount() == 0) {
						faveTable.getSelectionModel().setSelectionInterval(faveTable.rowAtPoint(arg0.getPoint()),
								faveTable.rowAtPoint(arg0.getPoint()));
					}
					if (faveTable.getSelectedRowCount() > 0) {
						favepop.show(faveTable, arg0.getX() + 10, arg0.getY());
					}
				}
			}

		});
		faveTable.addKeyListener(searchKeyListen);
		faveTable.getInputMap(JComponent.WHEN_FOCUSED)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK, true), "CTRL C");
		faveTable.getActionMap().put("CTRL C", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				copyToClipboard(faveTable); // whole row is copied instead of
											// only Cell
			}
		});
		faveTable.setFillsViewportHeight(true);
		final JScrollPane abc = new JScrollPane(faveTable);
		return abc;
	}

	private void rightRenderer() {
		rightRenderer(table, showThumb.isSelected());
		rightRenderer(faveTable, showfaveThumb.isSelected());
	}

	private void rightRenderer(JTable table, boolean showThumbspls) {
		if (showThumbspls) {
			if (!table.getDefaultRenderer(URL.class).equals(imgrenderer)) {
				int i = URLRenderer.getURLColumn(table);
				table.getColumnModel().getColumn(i).setWidth(122);
				table.getColumnModel().getColumn(i).setMaxWidth(122);
				table.getColumnModel().getColumn(i).setMinWidth(122);
				table.setRowHeight(92);
				table.setDefaultRenderer(URL.class, imgrenderer);

			}
		} else {
			if (!table.getDefaultRenderer(URL.class).equals(urlrenderer)) {
				setColumnSizes(table);
				table.setDefaultRenderer(URL.class, urlrenderer);
			}
		}
	}

	private void initWin7() {
		onWin7 = System.getProperty("os.name").toString().equals("Windows 7");
		onWin7 = onWin7 || System.getProperty("os.name").toString().equals("Windows 8");
		if (!onWin7)
			return;
		try {
			list = COMRuntime.newInstance(ITaskbarList3.class);
			hwnd = (Pointer<Integer>) Pointer.pointerToAddress(com.sun.jna.Native.getComponentID(frame));

		} catch (ClassNotFoundException e) {
			onWin7 = false;
			log(e);
			e.printStackTrace();
		} catch (ClassCastException e) {
			onWin7 = false;
			log(e);
			e.printStackTrace();
		}
	}

	private void unsubscribe(String who) {
		// YouTubeService myService = new YouTubeService(AUTH_PROGRAM_NAME,
		// AUTH_KEY);
		// char[] p = pw.getPassword();
		// StringBuilder ppw = new StringBuilder(TEXT_NOTHING);
		// for (int i = 0; i < p.length; i++) {
		// ppw.append(p[i]);
		// p[i] = 0;
		// }
		// try {
		// myService.setUserCredentials(username.getText(), ppw.toString());
		// } catch (InvalidCredentialsException e) {
		//
		// } catch (AuthenticationException e) {
		//
		// }
		//
		// String feedUrl = LINK_USER + tubename.getText() + LINK_SUBS;
		// SubscriptionFeed feed = null;
		// try {
		// feed = myService.getFeed(new URL(feedUrl), SubscriptionFeed.class);
		//
		// while (feed != null) {
		// for (SubscriptionEntry subEntry : feed.getEntries()) {
		// if (who.equals(subEntry.getUsername())) {
		// subEntry.delete();
		// return;
		// }
		// }
		// if (feed.getNextLink() != null) {
		// feed = myService.getFeed(new URL(feed.getNextLink()
		// .getHref()), SubscriptionFeed.class);
		// } else {
		// feed = null;
		// }
		// }
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (ServiceException e) {
		// e.printStackTrace();
		// }
	}

	private void initmenu(ActionListener searchlisten, KeyListener searchKeyListen) {

		ImageIcon img = null;
		JButton searchBtn = new JButton();
		try {
			img = new ImageIcon(getClass().getClassLoader().getResource(ICON_FILE_SEARCH));
			if (img.getIconHeight() == ICON_SIZE_SMALL && img.getIconWidth() == ICON_SIZE_SMALL) {
				searchBtn.setIcon(img);
			}
		} catch (NullPointerException e) {
			log(e);
		}
		searchBtn.setToolTipText(TOOLTIP_SEARCH);
		searchBtn.setContentAreaFilled(false);
		searchBtn.setBorderPainted(false);
		searchBtn.setFocusPainted(false);
		searchBtn.setPreferredSize(new Dimension(32, 10));
		searchBtn.addActionListener(searchlisten);
		searchBtn.addKeyListener(searchKeyListen);

		searchField.setToolTipText(TOOLTIP_SEARCH);
		searchField.setPreferredSize(new Dimension(130, searchField.getPreferredSize().height));
		searchField.setSize(searchField.getPreferredSize());
		searchField.setMaximumSize(searchField.getSize());
		searchField.addActionListener(searchlisten);

		showOnlyNew = new JCheckBox(TEXT_ONLYNEW);
		showOnlyNew.setToolTipText(TOOLTIP_ONLYNEW);
		// showOnlyNew.addKeyListener(searchKeyListen);
		showOnlyNew.addActionListener(searchlisten);
		showOnlyNew.setOpaque(false);
		try {
			img = new ImageIcon(getClass().getClassLoader().getResource(ICON_FILE_SEARCH_KILL));
		} catch (NullPointerException e) {
			log(e);
		}
		JButton searchClearBtn = new JButton();
		searchClearBtn.setIcon(img);
		searchClearBtn.setContentAreaFilled(false);
		searchClearBtn.setBorderPainted(false);
		searchClearBtn.setFocusPainted(false);
		searchClearBtn.setPreferredSize(new Dimension(32, 10));
		searchClearBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchField.setText(TEXT_NOTHING);
				showOnlyNew.setSelected(false);
				newFilter(TEXT_NOTHING);
			}
		});
	}

	public JTable getCurrentFocussedTable() {
		return table.hasFocus() ? table : faveTable;
	}

	public JTable getCurrentVisibleTable() {
		return tabbo.getSelectedIndex() == TAB_TABLE_INDEX ? table : faveTable;
	}

	private ImageIcon getIcon(String iconFileHelp) {
		try {
			ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconFileHelp));
			return icon;
		} catch (NullPointerException e) {
			log(e);
		}
		return null;
	}

	private JPanel initoptions(ActionListener optionlisten, KeyListener searchKeyListen) {

		ChangeListener op = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// System.out.println("wub");
				// newstuff = true;
			}
		};

		autoupdate = new JCheckBox(TEXT_AUTO_UPDATE, Boolean.TRUE);
		autoupdate.setToolTipText(TOOLTIP_AUTOUPDATE);
		autoupdate.addKeyListener(searchKeyListen);
		autoupdate.addChangeListener(op);

		playSounds = new JCheckBox(TEXT_SOUNDS, Boolean.TRUE);
		playSounds.setToolTipText(TOOLTIP_SOUNDS);
		playSounds.setIcon(getIcon(ICON_FILE_AUDIO_OFF));
		playSounds.setSelectedIcon(getIcon(ICON_FILE_AUDIO_ON));
		playSounds.setDisabledSelectedIcon(getIcon(ICON_FILE_AUDIO_OFF));
		playSounds.setDisabledIcon(getIcon(ICON_FILE_AUDIO_OFF));
		playSounds.addKeyListener(searchKeyListen);
		playSounds.addChangeListener(op);

		wantTray = new JCheckBox(TEXT_TRAY, Boolean.TRUE);
		wantTray.setToolTipText(TOOLTIP_TRAY);
		wantTray.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				trayNotification.setEnabled(wantTray.isSelected());
			}
		});
		wantTray.addKeyListener(searchKeyListen);
		wantTray.addChangeListener(op);

		trayNotification = new JCheckBox(TEXT_TRAY_NOTE, Boolean.TRUE);
		trayNotification.setToolTipText(TOOLTIP_TRAY_NOTE);
		trayNotification.addKeyListener(searchKeyListen);
		trayNotification.addChangeListener(op);

		FocusListener sel = new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent arg0) {
				((JTextField) arg0.getComponent()).selectAll();
			}
		};

		JPanel p = new JPanel(new GridLayout(18, 1));
		JPanel t = new JPanel(new GridLayout(1, 2));

		tubename = new JTextField(15);
		tubename.addFocusListener(sel);
		tubename.addActionListener(optionlisten);
		tubename.addKeyListener(searchKeyListen);
		t.add(new JLabel(TEXT_TUBENAME));
		t.add(tubename);

		p.add(t);

		p.add(playSounds);
		p.add(autoupdate);

		startupupdate = new JCheckBox(TEXT_STARTUPUPDATE);
		startupupdate.setToolTipText(TOOLTIP_STARTUPUPDATE);
		startupupdate.addKeyListener(searchKeyListen);
		startupupdate.addChangeListener(op);
		p.add(startupupdate);

		wantTray.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (trayavailable) {
					if (!wantTray.isSelected()) {
						SystemTray.getSystemTray().remove(trayicon);
					} else {
						try {
							SystemTray.getSystemTray().add(trayicon);
						} catch (AWTException e) {
							log(e);
						}
					}
				} else {
					Exception e = new IllegalStateException("Tray stuff");
					log(e);
				}

			}
		});
		p.add(wantTray);
		p.add(trayNotification);

		backToFront.setText(TEXT_BACKTOTOP);
		backToFront.setToolTipText(TOOLTIP_BACKTOTOP);
		backToFront.setSelected(true);
		backToFront.addKeyListener(searchKeyListen);
		backToFront.addChangeListener(op);
		p.add(backToFront);

		reversePlaylist = new JCheckBox(TEXT_REVERSE);
		reversePlaylist.setToolTipText(TOOLTIP_REVERSE);
		reversePlaylist.setSelected(true);
		reversePlaylist.addKeyListener(searchKeyListen);
		reversePlaylist.addChangeListener(op);
		p.add(reversePlaylist);

		JPanel nuData = new JPanel(new GridLayout(1, 3));
		JPanel cont = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel ndata = new JLabel(TEXT_DATAAMOUNT[0]);
		ndata.setToolTipText(TOOLTIP_DATAAMOUNT);
		cont.add(ndata);
		nuData.add(cont);

		JPanel paa = new JPanel(new GridLayout(1, 2));

		startminimized = new JCheckBox(TEXT_STARTMINIMIZED);
		startminimized.setToolTipText(TOOLTIP_STARTMINIMIZED);
		startminimized.addKeyListener(searchKeyListen);
		startminimized.addChangeListener(op);
		paa.add(startminimized);

		multi = new JCheckBox("Multi Threaded update");
		multi.setToolTipText(TOOLTIP_MULTI);
		multi.setSelected(true);
		multi.addKeyListener(searchKeyListen);
		multi.addChangeListener(op);
		paa.add(multi);
		p.add(paa);

		JPanel ppa = new JPanel(new GridLayout(1, 2));

		showThumb = new JCheckBox("show thumbnails ( on subscribtions)");
		showThumb.setToolTipText("showThumbnails");
		showThumb.addKeyListener(searchKeyListen);
		showThumb.addChangeListener(op);
		// JPanel lef = new JPanel();
		// lef.setBorder(BorderFactory.createTitledBorder("subscribtion Tab"));
		// lef.add(showThumb);
		ppa.add(showThumb);

		showfaveThumb = new JCheckBox("show thumbnails (on favorites)");
		showfaveThumb.setToolTipText("showThumbnails");
		showfaveThumb.addKeyListener(searchKeyListen);
		showfaveThumb.addChangeListener(op);
		// JPanel righ = new JPanel();
		// righ.setBorder(BorderFactory.createTitledBorder("favorite Tab"));
		// righ.add(showfaveThumb);
		ppa.add(showfaveThumb);

		p.add(ppa);

		showData.setToolTipText(TOOLTIP_DATAAMOUNT);
		showData.setSize(50, showData.getSize().height);
		showData.setValue(50);
		showData.addChangeListener(op);
		nuData.add(showData);
		ndata = new JLabel(TEXT_DATAAMOUNT[1]);
		ndata.setToolTipText(TOOLTIP_DATAAMOUNT);
		nuData.add(ndata);

		nuData.setToolTipText(TOOLTIP_DATAAMOUNT);
		p.add(nuData);
		JPanel portpanel = new JPanel(new GridLayout(1, 3));
		portpanel.setToolTipText(TOOLTIP_PORT);
		portpanel.add(new JLabel(TEXT_PORT, SwingConstants.RIGHT));
		portField = new JTextField();
		portField.setToolTipText(TOOLTIP_PORT);
		for (int i = 0; i < portField.getKeyListeners().length; i++) {
			portField.removeKeyListener(portField.getKeyListeners()[i]);
		}
		portField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				arg0.consume();
				if (Character.isDigit(arg0.getKeyChar())) {
					if ((arg0.getKeyChar()) == KeyEvent.VK_BACK_SPACE) {
						try {
							portField.setText(portField.getText(0, portField.getText().length() - 1));
						} catch (BadLocationException e) {
							log(e);
							// cant believe this will ever trigger
						}

					} else {
						portField.setText(portField.getText() + arg0.getKeyChar());
					}
					portField.setCaretPosition(portField.getText().length());
					int po = Integer.valueOf(portField.getText());
					if (po > PORT_MAX) {
						portField.setText(TEXT_NOTHING + PORT_MAX);
					}
				}

			}

		});
		portField.addActionListener(optionlisten);
		portpanel.add(portField);
		portpanel.add(new JPanel());
		if (PORT_ENABLED) {
			p.add(portpanel);
		} else {
			p.add(new JPanel());
		}

		JPanel purge = new JPanel(new BorderLayout());
		purgeData = new JButton(TEXT_PURGE);
		purgeData.setToolTipText(TOOLTIP_PURGE);
		purgeData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				purgeConfirm.setEnabled(!purgeConfirm.isEnabled());
			}
		});
		purgeData.addKeyListener(searchKeyListen);
		purge.add(purgeData, BorderLayout.CENTER);
		purgeConfirm = new JButton(TEXT_PURGE_CONFIRM);
		purgeConfirm.setToolTipText(TOOLTIP_PURGE_CONFIRM);
		purgeConfirm.setEnabled(false);
		purgeConfirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model = new TestModel();
				table.setModel(model);
				saveLocalData();
				readLocalData(null);
				purgeConfirm.setEnabled(false);
				trayicon.resetcnt();
			}
		});
		purgeConfirm.addKeyListener(searchKeyListen);
		purge.add(purgeConfirm, BorderLayout.EAST);
		p.add(purge);
		// p.add(new JLabel());
		p.add(new JLabel());
		JButton btn = new JButton(TEXT_SAVE, getIcon(ICON_FILE_SAVE));
		btn.addActionListener(optionlisten);
		btn.setPreferredSize(new Dimension(btn.getPreferredSize().width * 2, btn.getPreferredSize().height));
		JPanel asdpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		asdpanel.add(btn);
		p.add(asdpanel);
		return p;
	}

	public static boolean isPrintable(char c) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		return (!Character.isISOControl(c)) && c != KeyEvent.CHAR_UNDEFINED && block != null
				&& block != Character.UnicodeBlock.SPECIALS;
	}

	private void initdebug() {

		debugbtn = new JButton() {
			@Override
			public void paint(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				// super.paintComponent(g2d);

				ImageIcon i = new ImageIcon(getClass().getClassLoader().getResource(ICON_FILE_COPY));
				g2d.setColor(Color.RED);
				g2d.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
				g2d.drawImage(i.getImage(), 0, 0, 50, 50, 0, 0, i.getIconWidth(), i.getIconHeight(), null);
			}
		};
		debugbtn.setBackground(Color.red);
		debugbtn.setPreferredSize(new Dimension(50, 50));
		debugbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(gpcont.getSize());
			}
		});
		bot.add(debugbtn, BorderLayout.EAST);
	}

	private void changeTaskBarProgressValue(int curr, int max) {
		if (onWin7) {
			list.SetProgressValue(hwnd, curr, max);
			win7Progress[0] = curr;
			win7Progress[1] = max;

		}
	}

	class VideoGrid extends JScrollPane implements TableModelListener {

		private TestModel mod;

		private HashMap<myVidEntry, JPanel> hm;

		private JPanel pan;

		private int DEBUG_CNT = 1000;

		private void search(String s) {
			myVidEntry tempve;
			// ArrayList<JPanel> list = new ArrayList<JPanel>();

			pan.removeAll();
			for (int i = 0; i < DEBUG_CNT; i++) {
				tempve = mod.getDa().get(i);
				if (!tempve.getUser().contains(s)) {
					pan.remove(hm.get(tempve));
				} else {
					pan.add(hm.get(tempve));
				}
			}
			// System.out.println(list);
			// for(int j=0;j<list.size();j++){
			// boolean b=false;
			// for(int i=0; i<pan.getComponents().length;i++){
			// if(pan.getComponent(i).equals(list.get(j))){
			// b=true;
			// }
			//
			// }
			// if(!b){
			// pan.add(list.get(j));
			// }
			// }

		}

		public VideoGrid(TestModel model) {
			super();
			getVerticalScrollBar().setUnitIncrement(15);
			hm = new HashMap<myVidEntry, JPanel>();
			JFrame asd = new JFrame();
			asd.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			// model.getDa().size()
			mod = model;
			pan = new JPanel(new GridLayout(0, 3, 5, 5));

			model.addTableModelListener(this);
			JPanel wu = null;
			for (int i = model.getDa().size() - 1; i >= 0; i--) {
				myVidEntry mv = model.getDa().get(i);
				wu = getMvEntry(mv);
				pan.add(wu);
				hm.put(mv, wu);
			}

			pan.setBackground(Color.GRAY);
			this.setViewportView(pan);

			asd.add(this);
			asd.setSize(wu.getPreferredSize().getSize().width * 3 + 15 * 3, 500);

			asd.setVisible(true);

			// long time=System.currentTimeMillis();
			// search("husky");
			//
			// search("Zeilla");
			//
			// search("husky");
			// search("Zeilla");
			// System.out.println(System.currentTimeMillis()-time);
		}

		@Override
		public void tableChanged(TableModelEvent arg0) {
			System.out.println(arg0);
			System.out.println(arg0.getColumn() == TableModelEvent.ALL_COLUMNS);
			System.out.println(arg0.getFirstRow());
			System.out.println(arg0.getLastRow());
			System.out.println(arg0.getType() == TableModelEvent.INSERT);
		}

		public void dataChanged() {
			pan.removeAll();
			for (int i = mod.getDa().size() - 1; i >= 0; i--) {
				myVidEntry tempve = mod.getDa().get(i);
				if (hm.containsKey(tempve)) {
					pan.add(hm.get(tempve));
				} else {
					JPanel jp = getMvEntry(tempve);
					pan.add(jp);
					hm.put(tempve, jp);
				}
			}
			pan.repaint();
		}

		private JPanel getMvEntry(myVidEntry tempve) {
			JLabel lbl;
			JPanel wu;
			ImagePanel ip;
			ip = new ImagePanel(ImageRenderer.getImage(tempve.getLink()), 2, 2, this);
			wu = new VidGridPanel(ip);
			lbl = new JLabel(tempve.getUser(), SwingConstants.CENTER);
			lbl.setMaximumSize(new Dimension(120, 20));
			lbl.setPreferredSize(new Dimension(120, 20));
			lbl.setSize(new Dimension(120, 20));
			wu.add(lbl);

			lbl = new JLabel(tempve.getTitle(), SwingConstants.CENTER);
			lbl.setMaximumSize(new Dimension(120, 20));
			lbl.setPreferredSize(new Dimension(120, 20));
			lbl.setSize(new Dimension(120, 20));
			wu.add(lbl);

			wu.add(ip);
			wu.addMouseListener(new VidGridMouseListener(tempve));
			wu.setBorder(BorderFactory.createLoweredBevelBorder());

			wu.setOpaque(true);
			if (tempve.isNew())
				wu.setBackground(oddnewColor);
			return wu;
		}

	}

	class VidGridMouseListener extends MouseAdapter {

		private myVidEntry mve;

		public VidGridMouseListener(myVidEntry v) {
			mve = v;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getButton() == MouseEvent.BUTTON1) {
				mve.setclicked(true);
				try {
					Desktop.getDesktop().browse(new URL(mve.getLink()).toURI());
					((VidGridPanel) arg0.getSource()).getImgp().setBooleans(true, mve.isNew());
					((Component) arg0.getSource()).repaint();
					MainPanel.stats.incnClick();

				} catch (Exception ex) {
					log(ex);
				}
			}
		}

	}

	class VideoTable extends JTable implements ImageObserver {
		myVidEntry mve = null;
		Component c;

		public VideoTable(TestModel model) {
			super(model);
		}

		@Override
		public JToolTip createToolTip() {
			MultiLineToolTip tip = new MultiLineToolTip();
			tip.setComponent(this);
			return tip;
		}

		@Override
		public Point getToolTipLocation(MouseEvent e) {
			return new Point(e.getX() + 15, e.getY());
		}

		// for whatever reason this doesnt work
		@Override
		public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height) {
			// If the image has finished loading, repaint the cell.
			if ((flags & ALLBITS) != 0) {
				// repaint(getCellRect(imgrenderer.get(image).row,imgrenderer.get(image).col,false));
				return false; // Return false to say we don't need further
								// notification.
			}

			return true; // Image has not finished loading, need further
							// notification.
		}

		@Override
		public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
			try {
				// prepareImage(ImageRenderer.getImage(getValueAt(row, urlcol)),
				// null);
				c = super.prepareRenderer(tcr, row, column);
				c.setForeground(getForeground());
				int urlcol = URLRenderer.getURLColumn(this);
				String url_str = this.getValueAt(row, urlcol) != null ? this.getValueAt(row, urlcol).toString()
						: TEXT_NOTHING;

				try {
					mve = ((TestModel) this.getModel()).getDatabyURL(new URL(url_str));
				} catch (MalformedURLException e) {
					log(e);
				}
				if (mve.isNew()) {
					c.setBackground(row % 2 == 0 ? evennewColor : oddnewColor);
				} else {
					c.setBackground(row % 2 == 0 ? evenColor : getBackground());
				}
				if (this.isRowSelected(row)) {
					c.setBackground(c.getBackground().darker());
				}

			} catch (NullPointerException e) {
				c = new JLabel(TEXT_NOTHING);
			}

			return c;
		}

		@Override
		public int getRowCount() {
			int a = ((Integer) showData.getValue()).intValue();
			int val = (a == 0 || a > super.getRowCount()) ? super.getRowCount() : a;
			return val;

		}
	}

	private Component initTable(KeyListener searchKeyListen) {
		table = new VideoTable(model);
		sorter = new TableRowSorter<TestModel>(model);
		table.setRowSorter(sorter);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setIntercellSpacing(new Dimension());
		table.setShowGrid(false);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setAutoCreateRowSorter(true);
		urlrenderer = new URLRenderer(logger, frame, backToFront, searchField, thisMainPanel);
		imgrenderer = new ImageRenderer(logger);
		table.setDefaultRenderer(URL.class, urlrenderer);
		table.setDefaultRenderer(char.class, new CenterRenderer());
		table.setDefaultRenderer(String.class, new LineWrapRenderer());

		table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer());

		table.addMouseListener(urlrenderer);
		table.addMouseMotionListener(urlrenderer);
		table.addKeyListener(searchKeyListen);

		table.getInputMap(JComponent.WHEN_FOCUSED)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK, true), "CTRL C");
		table.getActionMap().put("CTRL C", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				copyToClipboard(table); // whole row is copied instead of only
										// Cell
			}
		});

		initPopupMenus();

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					if (table.getSelectedRowCount() == 0) {
						table.getSelectionModel().setSelectionInterval(table.rowAtPoint(arg0.getPoint()),
								table.rowAtPoint(arg0.getPoint()));
					}
					if (table.getSelectedRowCount() > 0) {
						pop.show(table, arg0.getX() + 10, arg0.getY());
					}
				}
			}

		});
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		return scrollPane;
	}

	private class PlayListener implements ActionListener {
		JTable ctable;

		public PlayListener(JTable table) {
			ctable = table;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (ctable.getSelectedRowCount() == 1) {
				urlrenderer.mouse(ctable, 3, ctable.getSelectedRow());
			} else {
				createPlayList(ctable);
			}
		}
	}

	private class CopyListener implements ActionListener {
		JTable ctable;

		public CopyListener(JTable table) {
			ctable = table;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			copyToClipboard(ctable);
		}
	}

	private class MarkListener implements ActionListener {
		JTable ctable;
		boolean t;

		public MarkListener(JTable table, boolean a) {
			ctable = table;
			t = a;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (t) {
				ctable = table;
			} else {
				ctable = faveTable;
			}
			for (int i = 0; i < ctable.getSelectedRows().length; i++) {
				try {
					((TestModel) ctable.getModel())
							.getDatabyURL(
									new URL(ctable.getModel().getValueAt(ctable.getSelectedRows()[i], 3).toString()))
							.setclicked(true);
					ctable.repaint(ctable.getCellRect(ctable.getSelectedRows()[i], 3, false));
				} catch (MalformedURLException e) {
					log(new Exception(
							"invalid url: " + ctable.getModel().getValueAt(ctable.getSelectedRows()[i], 3).toString(),
							e));
				}
			}
		}
	}

	private class FaveListener implements ActionListener {
		JTable ctable;

		public FaveListener(JTable table) {
			ctable = table;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (int i = 0; i < ctable.getSelectedRows().length; i++) {
				try {
					faveModel.addTest(myVidEntry.faveGen(((TestModel) ctable.getModel())
							.getDatabyURL(new URL(ctable.getValueAt(ctable.getSelectedRows()[i], 3).toString()))));
				} catch (MalformedURLException e) {
					log(new Exception(
							"invalid url: " + ctable.getModel().getValueAt(ctable.getSelectedRows()[i], 3).toString(),
							e));
				}
			}
			faveTable.repaint();
		}
	}

	private class DeleteListener implements ActionListener {
		JTable ctable;

		public DeleteListener(JTable table) {
			ctable = table;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			URL[] urls = new URL[ctable.getSelectedRowCount()];
			for (int row = 0; row < urls.length; row++) {
				try {
					urls[row] = new URL(ctable.getValueAt(ctable.getSelectedRows()[row], 3).toString());
				} catch (MalformedURLException e) {
					log(new Exception(
							"invalid url: " + ctable.getModel().getValueAt(ctable.getSelectedRows()[row], 3).toString(),
							e));
				}
			}
			for (int row = 0; row < urls.length; row++) {

				Rectangle cellrec = ctable.getCellRect(row, 0, false);
				Rectangle repaintrec = new Rectangle(0, cellrec.y, ctable.getWidth(), ctable.getHeight() - cellrec.y);
				((TestModel) ctable.getModel()).delTest(((TestModel) ctable.getModel()).getDatabyURL(urls[row]));
				ctable.repaint(repaintrec);

			}
		}
	}

	private void initPopupMenus() {

		JMenuItem play = new JMenuItem(POP_PLAY, getIcon(ICON_FILE_PLAY));
		play.addActionListener(new PlayListener(table));
		pop.add(play);
		pop.addSeparator();

		JMenuItem copy = new JMenuItem(POP_COPY, getIcon(ICON_FILE_COPY));
		copy.addActionListener(new CopyListener(table));
		pop.add(copy);

		JMenuItem mark = new JMenuItem(POP_VIEWED, getIcon(ICON_FILE_VIEWED));
		mark.addActionListener(new MarkListener(table, true));
		pop.add(mark);

		JMenuItem fave = new JMenuItem(POP_TOFAVE, getIcon(ICON_FILE_FAVEADD));
		fave.addActionListener(new FaveListener(table));
		pop.add(fave);

		pop.addSeparator();

		JMenuItem dele = new JMenuItem(POP_DELETE, getIcon(ICON_FILE_DELETE));
		dele.addActionListener(new DeleteListener(table));
		pop.add(dele);

		JMenuItem faveplay = new JMenuItem(POP_PLAY, getIcon(ICON_FILE_PLAY));
		faveplay.addActionListener(new PlayListener(faveTable));
		favepop.add(faveplay);
		favepop.addSeparator();

		JMenuItem favecopy = new JMenuItem(POP_COPY, getIcon(ICON_FILE_COPY));
		favecopy.addActionListener(new CopyListener(faveTable));
		favepop.add(favecopy);

		JMenuItem favemark = new JMenuItem(POP_VIEWED, getIcon(ICON_FILE_VIEWED));
		favemark.addActionListener(new MarkListener(faveTable, false));
		favepop.add(favemark);

		favepop.addSeparator();

		JMenuItem favedele = new JMenuItem(POP_DELETE, getIcon(ICON_FILE_DELETE));
		favedele.addActionListener(new DeleteListener(faveTable));
		favepop.add(favedele);

		JMenuItem infocopy = new JMenuItem(POP_INFOCOPY, getIcon(ICON_FILE_COPY));
		infocopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clipboard.setContents(new StringSelection(info.getText()), thisMainPanel);
			}

		});
		infopop.add(infocopy);

	}

	public void copyToClipboard(JTable ctaable) {
		StringBuilder str = new StringBuilder("");
		String[] url = getSelectionURLS(ctaable);
		for (int i = 0; i < url.length; i++) {
			str.append(url[i]);
			if (i != ctaable.getSelectedRowCount() - 1) {
				str.append('\n');
			}
		}
		StringSelection strsel = new StringSelection(str.toString());
		clipboard.setContents(strsel, thisMainPanel);
	}

	public void createPlayList(JTable ctaable) {
		String[] urls = justIDs(getSelectionURLS(ctaable));

		if (reversePlaylist.isSelected()) {
			String[] switchedurls = new String[urls.length];
			for (int i = 0; i < urls.length; i++) {
				switchedurls[urls.length - i - 1] = urls[i];
			}
			playList(switchedurls);
		} else {
			playList(urls);
		}

	}

	public String[] getSelectionURLS(JTable ctaable) {
		String[] url = new String[ctaable.getSelectedRows().length];
		for (int i = 0; i < ctaable.getSelectedRows().length; i++) {
			url[i] = ctaable.getValueAt(ctaable.getSelectedRows()[i], 3).toString();
		}
		return url;
	}

	private String[] justIDs(String[] url) {
		for (int i = 0; i < url.length; i++) {
			url[i] = url[i].substring(url[i].lastIndexOf('=') + 1);
		}
		return url;
	}

	public class JumpBackListener implements WindowFocusListener {
		@Override
		public void windowGainedFocus(WindowEvent arg0) {
			// do nothing
		}

		@Override
		public void windowLostFocus(WindowEvent arg0) {
			frame.toFront();
			frame.removeWindowFocusListener(this);
		}
	}

	public void playList(final String[] urls) {
		new Thread() {
			@Override
			public void run() {
				info.setText(PLAYLIST_CREATING[0]);
				List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
				Credential credential = null;
				try {
					credential = Auth.authorize(scopes, "playlistupdates");
				} catch (IOException e1) {
					log(e1);
					e1.printStackTrace();
				}

				youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
						.setApplicationName(AUTH_PROGRAM_NAME).build();

				try {
					PlaylistSnippet playlistSnippet = new PlaylistSnippet();
					playlistSnippet.setTitle("Test Playlist " + Calendar.getInstance().getTime());
					playlistSnippet.setDescription("A private playlist created with the YouTube API v3");
					PlaylistStatus playlistStatus = new PlaylistStatus();
					playlistStatus.setPrivacyStatus("private");

					Playlist youTubePlaylist = new Playlist();
					youTubePlaylist.setSnippet(playlistSnippet);
					youTubePlaylist.setStatus(playlistStatus);

					// Call the API to insert the new playlist. In the API call, the first
					// argument identifies the resource parts that the API response should
					// contain, and the second argument is the playlist being inserted.
					YouTube.Playlists.Insert playlistInsertCommand = youtube.playlists().insert("snippet,status",
							youTubePlaylist);
					Playlist playlistInserted = playlistInsertCommand.execute();

					String playlistId = playlistInserted.getId();

					for (String string : urls) {
						System.out.println(string);
						insertPlaylistItem(playlistId, string);
					}

					if (backToFront.isSelected()) {
						frame.addWindowFocusListener(new JumpBackListener());
					}
					// Desktop.getDesktop().browse(
					// new URI(myPlaylist.getHtmlLink().getHref()));
					// TODO
					info.setText(TEXT_NOTHING);

				} catch (IOException e) {
					info.setText(ERROR_PLAYLIST);
					log(e);
				}
			}
		}.start();

	}

	private static String insertPlaylistItem(String playlistId, String videoId) throws IOException {

		// Define a resourceId that identifies the video being added to the
		// playlist.
		ResourceId resourceId = new ResourceId();
		resourceId.setKind("youtube#video");
		resourceId.setVideoId(videoId);

		// Set fields included in the playlistItem resource's "snippet" part.
		PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
		playlistItemSnippet.setTitle("First video in the test playlist");
		playlistItemSnippet.setPlaylistId(playlistId);
		playlistItemSnippet.setResourceId(resourceId);

		// Create the playlistItem resource and set its snippet to the
		// object created above.
		PlaylistItem playlistItem = new PlaylistItem();
		playlistItem.setSnippet(playlistItemSnippet);

		// Call the API to add the playlist item to the specified playlist.
		// In the API call, the first argument identifies the resource parts
		// that the API response should contain, and the second argument is
		// the playlist item being inserted.
		YouTube.PlaylistItems.Insert playlistItemsInsertCommand = youtube.playlistItems()
				.insert("snippet,contentDetails", playlistItem);
		PlaylistItem returnedPlaylistItem = playlistItemsInsertCommand.execute();

		// item's unique playlistItem ID.
		return returnedPlaylistItem.getId();

	}

	protected void exitthis() {
		frame.setVisible(false);
		if (playSounds.isSelected()) {
			sound_end.start();
		}
		if (serverthread != null && serverthread.isAlive()) {
			try {
				serverthread.server.close();
			} catch (IOException e) {
				log(e);
			}
		}
		if (trayavailable && trayicon != null) {
			SystemTray.getSystemTray().remove(trayicon);
		}
		if (updateThread != null && updateThread.isAlive()) {
			updateThread.interrupt();
		}
		if (logger != null && exceptioncnt > 0) {
			logger.print(LOGGER[1]);
			logger.print(LOGGER[2]);
			logger.close();
		}
		if (onWin7) {
			list.Release();
		}

		saveLocalData();
		new Thread() {
			@Override
			public void run() {
				setName("ExitThread");
				try {
					Thread.sleep(playSounds.isSelected() ? SOUND_FILE_END_TIME : 0);
				} catch (InterruptedException e) {
				}
				try {
					sound_end.close();
				} catch (Exception e) {
					System.exit(0);
				}
				System.exit(0);
			}
		}.start();
	}

	String stringtoHTML(String a) {
		String[] b = a.split("\n");
		StringBuilder j = new StringBuilder();
		j.append("<html><body>");
		for (int i = 0; i < b.length; i++) {
			j.append(b[i] + "<br/>");
		}
		j.append("</html></body>");
		return j.toString();

	}

	private void initTray(ActionListener exitlisten, ActionListener optionlisten, ActionListener updatelisten) {
		PopupMenu o = new PopupMenu();
		MenuItem m = new MenuItem(TEXT_OPEN);
		m.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(true);
				frame.setState(Frame.NORMAL);
				frame.repaint();
				trayicon.resetcnt();

			}
		});
		o.add(m);

		updateMenuItem = new MenuItem(TEXT_UPDATE);
		updateMenuItem.addActionListener(updatelisten);
		o.add(updateMenuItem);

		m = new MenuItem(TEXT_OPTIONS);
		m.addActionListener(optionlisten);
		o.add(m);

		m = new MenuItem(TEXT_RESETCOUNTER);
		m.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				trayicon.resetcnt();

			}
		});
		o.add(m);
		o.addSeparator();
		m = new MenuItem(TEXT_EXIT);
		m.addActionListener(exitlisten);
		o.add(m);
		ImageIcon asdicon = null;
		try {
			asdicon = new ImageIcon(getClass().getClassLoader().getResource(ICON_FILE_TRAY));
			if (asdicon.getIconHeight() == ICON_SIZE_SMALL && asdicon.getIconWidth() == ICON_SIZE_SMALL) {
				trayicon = new CountingTrayIcon(frame, asdicon.getImage(), AUTH_PROGRAM_NAME, o);
			} else {
				trayicon = new CountingTrayIcon(frame, Toolkit.getDefaultToolkit().getImage("trayIcon.jpg"),
						AUTH_PROGRAM_NAME, o);
				log(new Exception(ERROR_NOT_SQUARE));
			}
		} catch (NullPointerException e) {
			trayicon = new CountingTrayIcon(frame, Toolkit.getDefaultToolkit().getImage("trayIcon.jpg"),
					AUTH_PROGRAM_NAME, o);
			log(new Exception("icon not found", e));
		}

		trayicon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getWhen() - trayicon.lastClick > 10) {
					frame.setVisible(true);
					frame.setState(Frame.NORMAL);
					frame.repaint();
				}
			}
		});

		SystemTray tray = null;
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();
			try {
				if (wantTray.isSelected()) {
					tray.add(trayicon);
				}
				trayavailable = true;
			} catch (AWTException e1) {
				log(e1);
			}
		} else {
			wantTray.setSelected(false);
			wantTray.setEnabled(false);
			wantTray.setToolTipText(TOOLTIP_TRAY_UNAVAILABLE);
			trayNotification.setSelected(false);
			trayNotification.setEnabled(false);
			trayNotification.setToolTipText(TOOLTIP_TRAY_UNAVAILABLE);
		}
	}

	private void initSounds() {
		try {
			URL soundURL = getClass().getClassLoader().getResource(SOUND_FILE_NEW);
			// System.out.println(soundURL.getFile());
			sound_new = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			sound_new.open(AudioSystem.getAudioInputStream(soundURL));
			FloatControl volume = (FloatControl) sound_new.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-15);

			soundURL = getClass().getClassLoader().getResource(SOUND_FILE_START);
			sound_start = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			sound_start.open(AudioSystem.getAudioInputStream(soundURL));
			volume = (FloatControl) sound_start.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-15);

			soundURL = getClass().getClassLoader().getResource(SOUND_FILE_END);
			sound_end = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			sound_end.open(AudioSystem.getAudioInputStream(soundURL));
			volume = (FloatControl) sound_end.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-15);

		} catch (LineUnavailableException e1) {
			corruptSound(e1);
		} catch (UnsupportedAudioFileException ee) {
			corruptSound(ee);
		} catch (IOException ae) {
			corruptSound(ae);
		} catch (NullPointerException ae) {
			corruptSound(ae);
		} catch (IllegalArgumentException ae) {
			corruptSound(ae);
		}
	}

	public void updateGo() {
		if (updateThread == null || !updateThread.isAlive()) {
			model.clearNew();
			updateThread = new UpdateThread();
			updateThread.start();
		}
	}

	public void log(Exception e) {
		e.printStackTrace();
		if (exceptioncnt == 0) {
			logger.println(LOGGER[0]);
			logger.println("\n\t" + dumpOptions());
		}
		exceptioncnt++;
		logger.println(new Time(new Date().getTime()).toString());

		logger.println(e.getMessage());
		e.printStackTrace(logger);
		logger.println(LOGGER[1]);
		logger.println(TEXT_TRIPLELINEWRAP);
		if (trayavailable && wantTray.isSelected() && trayNotification.isSelected()) {
			trayicon.displayMessage(AUTH_PROGRAM_NAME, "Error:\n" + e.getMessage() + "\nsee logfile for details",
					MessageType.ERROR);
		}

		System.err.println("\n" + ERROR_LOGGED);
	}

	public String dumpOptions() {
		StringBuilder a = new StringBuilder(System.getProperty("os.name").toString());
		a.append("\nAutoUpdate: ");
		a.append(autoupdate.isSelected());
		a.append("\nMultiThreaded: ");
		a.append(multi.isSelected() + "\n");
		return a.toString();
	}

	private void corruptSound(Exception e) {
		playSounds.setSelected(false);
		playSounds.setEnabled(false);
		update.setText(ERROR_SOUND);
		log(e);
	}

	private void resetSound(Clip c) {
		c.setFramePosition(0);
	}

	private void playSound(Clip c) {
		resetSound(c);
		c.start();
	}

	class UpdateThread extends Thread {
		public boolean interrupted_up = false;

		public UpdateThread() {
			setName(UpdateThread.class.getCanonicalName());
		}

		@Override
		public void run() {
			synchronized (update) {
				update.setEnabled(false);
				update.setText(TEXT_UPDATE_RUNNING);
			}
			updateMenuItem.setEnabled(false);

			TimeoutThread to = new TimeoutThread(updateThread, thisMainPanel);
			try {
				getData(to);
			} catch (MalformedURLException e) {
				to.end();
				restoreBtn(ERROR_UPDATE_URL);
				log(e);
			} catch (IOException e) {
				to.end();
				restoreBtn(ERROR_UPDATE_IO);
				log(e);
			} catch (ServiceException e) {
				to.end();
				restoreBtn(ERROR_UPDATE + e.getMessage());
				log(e);

			}

			saveLocalData();
		}

		@Override
		public void interrupt() {
			interrupted_up = true;
		}

		@Override
		public boolean isInterrupted() {
			return interrupted_up;
		}
	}

	private static void readPORT() {
		if (!PORT_ENABLED) {
			PORT = 0;
			return;
		}

		try {
			File fl = new File(FILE_NAME_DATA);
			if (fl.exists()) {
				ObjectInputStream inobj = new ObjectInputStream(new FileInputStream(fl));
				String b = ((String) inobj.readObject());

				if (Character.isDigit(b.charAt(0))) {
					PORT = Integer.valueOf(b);
				}
				inobj.close();
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
	}

	@SuppressWarnings("unchecked")
	private void readLocalData(StartProgressBar jb) {
		if (noload) {
			return;
		}
		long time = System.currentTimeMillis();
		try {
			File fl = new File(FILE_NAME_DATA);
			if (!fl.exists()) {
				saveLocalData();
			}
			ObjectInputStream inobj = new ObjectInputStream(new FileInputStream(fl));

			String b = ((String) inobj.readObject());

			if (Character.isDigit(b.charAt(0))) {
				portField.setText(b);
				b = ((String) inobj.readObject());

			} else {
				portField.setText(PORT + TEXT_NOTHING);
			}

			autoupdate.setSelected(b.equals(BOOLEAN_STRING_TRUE));
			b = ((String) inobj.readObject());
			playSounds.setSelected(b.equals(BOOLEAN_STRING_TRUE));
			int a = ((Integer) inobj.readObject()).intValue();
			int x = a / 10;
			for (int i = 0; i < a; i++) {
				myVidEntry mve = (myVidEntry) inobj.readObject();
				model.addTest(mve);
				if (i % x == 0 && jb != null) {
					jb.setValue(jb.getValue() + 5);
				}
			}
			b = ((String) inobj.readObject());
			wantTray.setSelected(b.equals(BOOLEAN_STRING_TRUE));

			b = ((String) inobj.readObject());
			trayNotification.setSelected(b.equals(BOOLEAN_STRING_TRUE));
			trayNotification.setEnabled(wantTray.isSelected());

			b = ((String) inobj.readObject());
			tubename.setText(b);

			b = ((String) inobj.readObject());
//			username.setText(b);

			b = ((String) inobj.readObject());
//			savepassword.setSelected(b.equals(BOOLEAN_STRING_TRUE));

//			if (savepassword.isSelected()) {
//				Base64 dec = new Base64();
//				String dpw = new String((byte[]) dec.decode(inobj.readObject()));
//				pw.setText(dpw);
//				dpw = TEXT_NOTHING;
//			}
			
			Rectangle rec = (Rectangle) inobj.readObject();
			frame.setBounds(rec);
			frame.setPreferredSize(new Dimension(rec.getSize()));

			ArrayList<myVidEntry> blacklist = (((ArrayList<myVidEntry>) inobj.readObject()));
			model.setBlackList(blacklist);

			b = ((String) inobj.readObject());
			backToFront.setSelected(b.equals(BOOLEAN_STRING_TRUE));

			a = ((Integer) inobj.readObject()).intValue();
			showData.setValue(a);

			b = ((String) inobj.readObject());
			startminimized.setSelected(b.equals(BOOLEAN_STRING_TRUE));

			b = ((String) inobj.readObject());
			startupupdate.setSelected(b.equals(BOOLEAN_STRING_TRUE));

			b = ((String) inobj.readObject());
			showThumb.setSelected(b.equals(BOOLEAN_STRING_TRUE));

			b = ((String) inobj.readObject());
			showfaveThumb.setSelected(b.equals(BOOLEAN_STRING_TRUE));

			ArrayList<myVidEntry> ba = (((ArrayList<myVidEntry>) inobj.readObject()));
			for (myVidEntry en : ba) {
				faveModel.addTest(en);
			}

			ba = (((ArrayList<myVidEntry>) inobj.readObject()));
			faveModel.setBlackList(ba);

			b = ((String) inobj.readObject());
			multi.setSelected(b.equals(BOOLEAN_STRING_TRUE));

			stats = ((Stats) inobj.readObject());

			b = ((String) inobj.readObject());
			reversePlaylist.setSelected(b.equals(BOOLEAN_STRING_TRUE));

			// newversion -> always false or EOF so no need to setup a variable
			inobj.readObject();
			newVersion = false;

			inobj.close();
		} catch (FileNotFoundException e) {
			handleReadLocalDataException(e);
			return;
		} catch (IOException e) {
			if (!(e instanceof EOFException)) {
				update.setText(ERROR_LOCALDATA_IO);
				update.setEnabled(false);
				log(e);
			} else {
				newVersion = true;
			}
			return;
		} catch (ClassNotFoundException e) {
			handleReadLocalDataException(e);
			update.setText(ERROR_CORRUPTDATA);
			return;
		} catch (ClassCastException e) {
			newVersion = true;
			return;
		} 
		if (System.currentTimeMillis() - time > LONG_TIME) {
			update.setText(TEXT_LOADING_LONG);
		}
	}

	private void handleReadLocalDataException(Exception e) {
		boolean b = nosave;
		nosave = false;
		saveLocalData();
		nosave = b;
		update.setText(ERROR_CORRUPTDATA);
		readLocalData(null);
		log(e);
	}

	// colIndex specifies a column in model.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sortAllRowsBy(TestModel model, int colIndex, boolean ascending) {
		Vector data = model.getDataVector();
		Collections.sort(data, new ColumnSorter(colIndex, ascending));
		newFilter(searchField.getText());
		// Collections.sort(model.getDa());
		// model.fireTableStructureChanged();
	}

	public void restorePB() {
		// bot.remove(update);
		updateProgress.setString(TEXT_UPDATE_RUNNING + 0 + TEXT_PERCENT);
		bot.add(updateProgress, BorderLayout.NORTH);
		updateProgress.setValue(0);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void restoreWinPB(ITaskbarList3.TbpFlag flag) {
		if (onWin7) {
			if (flag.equals(ITaskbarList3.TbpFlag.TBPF_ERROR)) {
				changeTaskBarProgressValue(1, 1);
			} else {
				changeTaskBarProgressValue(0, 1);
			}
			list.SetProgressState(hwnd, flag);
		}
	}

	public void restoreBtn(String a) {
		bot.remove(updateProgress);

		// bot.add(update,BorderLayout.SOUTH);
		update.setEnabled(true);
		update.setText(a);
		if (wantTray.isSelected()) {
			updateMenuItem.setLabel(TEXT_UPDATE);
			updateMenuItem.setEnabled(true);
		}

	}

	private void setColumnSizes() {
		setColumnSizes(table);
		setColumnSizes(faveTable);
	}

	private void setColumnSizes(JTable ta) {
		int wid = ta.getWidth();
		ta.setRowHeight(16);
		TableColumn col = ta.getColumnModel().getColumn(0);
		col.setPreferredWidth(wid / 15 * 2);
		col.setMaxWidth(120);
		col.setMinWidth(100);
		col = ta.getColumnModel().getColumn(1);
		col.setPreferredWidth(wid / 13 * 2);
		col = ta.getColumnModel().getColumn(2);
		col.setPreferredWidth(wid / 13 * 4);
		col = ta.getColumnModel().getColumn(3);
		col.setPreferredWidth(wid / 13 * 2);
		col.setMinWidth(100);
		// --> removed delcolumn
		// col = table.getColumnModel().getColumn(4);
		// col.setMaxWidth(30);
		col.setMinWidth(30);

	}

	private void updateProgressBars(double curr, double max, int nnew, String username) {
		int val = (int) (100 / max * curr);
		int cur = (int) curr;
		synchronized (updateProgress) {
			updateProgress.setValue(cur);
			changeTaskBarProgressValue(cur, (int) max);
			updateProgress.setString(TEXT_UPDATE_RUNNING + val + TEXT_PERCENT);
			synchronized (update) {
				update.setText(username + TEXT_NEW + nnew);
			}
			if (wantTray.isSelected()) {
				synchronized (updateMenuItem) {
					updateMenuItem.setLabel(TEXT_TRAY_UPDATE_RUNNING + val + TEXT_PERCENT);
				}
				synchronized (trayicon) {
					trayicon.updateupdateTooltip(val);
				}
			}
		}
	}

	private void updateProgressBars(double curr, double max, int nnew) {
		updateProgressBars(curr, max, nnew, "");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void updatePBinderterminate(boolean b) {
		updateProgress.setIndeterminate(b);
		if (onWin7) {
			if (b) {
				list.SetProgressState(hwnd, ITaskbarList3.TbpFlag.TBPF_INDETERMINATE);
			} else {
				list.SetProgressState(hwnd, ITaskbarList3.TbpFlag.TBPF_NORMAL);
			}
		}
	}

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String CLIENT_SECRETS = "client_secret.json";
	private static final Collection<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/youtube.readonly");

	public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
		// Load client secrets.
		// InputStream in = MainPanel.class.getResourceAsStream(CLIENT_SECRETS);
		InputStream in = new FileInputStream("D:/" + CLIENT_SECRETS);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
				clientSecrets, SCOPES).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		return credential;
	}

	public static YouTube getService() throws GeneralSecurityException, IOException {
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		Credential credential = null;
		try {
			credential = Auth.authorize(Lists.newArrayList("https://www.googleapis.com/auth/youtube"),
					"mYsubsYTService");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Credential credential = authorize(httpTransport);
		return new YouTube.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(AUTH_PROGRAM_NAME)
				.build();
	}

	private void getData(TimeoutThread to) throws MalformedURLException, IOException, ServiceException {

		long time = System.currentTimeMillis();
		restorePB();
		updatePBinderterminate(true);
		update.setText(TEXT_UPDATE_RUNNING);
		update.setEnabled(false);
		if (!multi.isSelected()) {
			to.start();
		}

		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
		// Credential credential = Auth.authorize(scopes, "listsubscription");

		try {
			youtube = getService();
		} catch (GeneralSecurityException e2) {
			log(e2);
			e2.printStackTrace();
		}
		int m = 0;
		List<Subscription> allfeeds = new ArrayList<>();
		try {
			HashMap<String, String> parameters = new HashMap<>();

			parameters.put("part", "snippet");
			YouTube.Channels.List channelsListMineRequest = youtube.channels().list(parameters.get("part").toString());
			channelsListMineRequest.setMine(true);
			ChannelListResponse response = channelsListMineRequest.execute();
			String channelId = response.getItems().get(0).getId();

			parameters.clear();
			parameters.put("part", "snippet,contentDetails");
			parameters.put("channelId", channelId);

			YouTube.Subscriptions.List subscriptionsListByChannelIdRequest = youtube.subscriptions()
					.list(parameters.get("part").toString());
			subscriptionsListByChannelIdRequest.setMaxResults(50l);

			SubscriptionListResponse subResponse;

			do {
				to.bam();
				subResponse = subscriptionsListByChannelIdRequest.setMine(true).execute();
				allfeeds.addAll(subResponse.getItems());
				m += subResponse.getItems().size();
				// subscriptionsListByChannelIdRequest.get(0)
				if (subResponse.getNextPageToken() == null)
					break;
				subscriptionsListByChannelIdRequest.setPageToken(subResponse.getNextPageToken());
				// parameters.put("nextPageToken",something);
			} while (true);

		} catch (GoogleJsonResponseException e) {
			System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
			e.printStackTrace();

		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
		} catch (Throwable t) {
			System.err.println("Throwable: " + t.getMessage());
			t.printStackTrace();
		}

		update.setText("updating " + m + " channel");
		updatePBinderterminate(false);
		updateProgress.setMaximum(allfeeds.size());

		Exception ex = null;

		List<List<Subscription>> li = Lists.partition(allfeeds, COUNT_PER_THREAD);

		final CallerThread[] thr = new CallerThread[li.size()];
		for (int k = 0; k < thr.length; k++) {
			thr[k] = new CallerThread(li.get(k), youtube);
			thr[k].setName(CallerThread.class.getCanonicalName() + " " + k);
			thr[k].start();
		}

		int curr;
		int alive;
		alive = li.size();

		while (true) {

			curr = 0;
			cnt = 0;
			for (int k = 0; k < thr.length; k++) {
				curr += thr[k].getcurr();
				cnt += thr[k].getcnt();
			}
			updateProgressBars(curr, m, cnt);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				log(e1);
			}
			if (alive == 0) {
				break;
			}
			alive = 0;
			for (int k = 0; k < thr.length; k++) {
				if (thr[k].isAlive()) {
					alive++;
				}
			}
		}
		for (int k = 0; k < thr.length; k++) {
			if (thr[k].getEx() != null) {
				ex = thr[k].getEx();
				break;
			}
		}

		if (ex != null) {
			log(new FetchException("one or more channel are temporary unavailable, try again in a few minutes",
					ex.getCause()));
		}

		/*
		 * v07 for (SubscriptionEntry entry : allfeeds) { if
		 * (updateThread.isInterrupted()) break; to.bam(); i++; // videoFeed =
		 * myService.getFeed(new URL(entry.getFeedUrl()), // VideoFeed.class);
		 * 
		 * // if (videoFeed.getTitle().getPlainText().contains("Uploads")) // { // cnt
		 * += printVideoFeed(myService, entry.getFeedUrl()); // } else { String[] a =
		 * entry.getTitle().getPlainText().split(TEXT_SPACE);
		 * 
		 * String b = LINK_USER + a[a.length - 1] + LINK_UPLOADS; cnt +=
		 * printVideoFeed(myService, b);
		 * 
		 * // } updateProgressBars(i, m, cnt); }
		 */

		to.fertig = true;
		time = System.currentTimeMillis() - time;
		if (debug == 0 && vidgr != null)
			vidgr.dataChanged();
		Time now = new Time(new Date().getTime());
		restoreBtn("updated " + m + " channels at " + now.toString().substring(0, now.toString().lastIndexOf(':'))
				+ " in " + (int) (time / 1000) + "s | new: " + cnt);
		stats.incnNew(cnt, model.getDa().size());
		stats.incnUpdates();
		if (cnt > 0) {
			if (playSounds.isSelected()) {
				try {
					playSound(sound_new);
				} catch (Exception e) {
					log(e);
				}
			}

			sortAllRowsBy(model, 0, false);

		} else {
			newFilter(searchField.getText());
		}
		restoreWinPB(ITaskbarList3.TbpFlag.TBPF_NOPROGRESS);
		if (trayavailable && wantTray.isSelected()) {
			trayicon.updateall(cnt, trayNotification.isSelected());
		}
		cnt = 0;
	}

	@SuppressWarnings("unchecked")
	void newFilter(String search) {
		MyRowFilter<TestModel, Object> rf = new MyRowFilter<TestModel, Object>();
		int a = -1;
		int b = -1;
		for (int i = 0; i < model.getColumnCount(); i++) {
			if (model.getColumnClass(i).equals(String.class)) {
				if (a == -1) {
					a = i;
				} else if (b == -1) {
					b = i;
				} else {
					rf.init(a, b, i, search, showOnlyNew.isSelected());
				}
			}
		}

		sorter.setRowFilter(rf);
		table.setRowSorter(sorter);

		rf = new MyRowFilter<TestModel, Object>();
		a = -1;
		b = -1;
		for (int i = 0; i < faveModel.getColumnCount(); i++) {
			if (faveModel.getColumnClass(i).equals(String.class)) {
				if (a == -1) {
					a = i;
				} else if (b == -1) {
					b = i;
				} else {
					rf.init(a, b, i, search, showOnlyNew.isSelected());
				}
			}
		}

		faveSorter.setRowFilter(rf);
		faveTable.setRowSorter(faveSorter);

	}

	// synchronized
	int printVideoFeed(YouTube service, Subscription subentry) throws IOException, ServiceException, FetchException {
		int i = 0;
		String chanid = subentry.getSnippet().getResourceId().getChannelId();
		ChannelListResponse chanr = youtube.channels().list("contentDetails").setId(chanid).setMaxResults(50l)
				.execute();
		String uplPlayId = "";

		String token = null;
		while (true) {

			for (Channel chan : chanr.getItems()) {
				uplPlayId = chan.getContentDetails().getRelatedPlaylists().getUploads();
			}
			token = chanr.getNextPageToken();
			if (token == null)
				break;
			youtube.channels().list("contentDetails").setId(chanid).setPageToken(token);
		}
		;

		token = null;
		PlaylistItemListResponse pliR = youtube.playlistItems().list("snippet,contentDetails").setPlaylistId(uplPlayId)
				.setMaxResults(50l).execute();
		while (true) {
			for (PlaylistItem pi : pliR.getItems()) {
				// System.out.println(pi.getSnippet().getTitle());
				myVidEntry x = new myVidEntry(pi);
				if (!model.getDa().contains(x) && !model.getBlack().contains(x)) {
					model.addTest(x);
					i++;
				}
			}
			if (fetchall)
				token = pliR.getNextPageToken();
			if (token == null)
				break;
			pliR = youtube.playlistItems().list("snippet,contentDetails").setPlaylistId(uplPlayId).setMaxResults(50l)
					.setPageToken(token).execute();
		}
		;

		// PlaylistListResponse response =
		// youtube.playlists().list("snippet").setChannelId(chanid).setMaxResults(50l).execute();
		// for (Playlist pl : response.getItems()) {
		// System.out.println(pl.getSnippet().getTitle());
		// }
		return i;
		// try {
		// String part = "snippet,contentDetails";
		// service.videos().list(part);
		// VideoFeed videoFeed = service.getFeed(new URL(subentry), VideoFeed.class);
		// int i = 0;
		// synchronized (model) {
		// for (VideoEntry ve : videoFeed.getEntries()) {
		// myVidEntry x = new myVidEntry(ve);
		// if (!model.getDa().contains(x) && !model.getBlack().contains(x)) {
		// model.addTest(x);
		// i++;
		// }
		// }
		// }
		// update.setEnabled(false); // redundant
		// return i;
		// } catch (ServiceForbiddenException e) {
		// if (e.getInternalReason().contains("User account closed")) {
		// if (trayavailable && wantTray.isSelected() && trayNotification.isSelected())
		// {
		// trayicon.displayMessage(MainPanel.AUTH_PROGRAM_NAME,
		// "the account "
		// + subentry.substring(LINK_USER.length(), subentry.length() -
		// LINK_UPLOADS.length())
		// + " does no longer exist",
		// MessageType.INFO);
		// }
		// } else {
		// log(e);
		// }
		// return 0;
		// } catch (Exception e) {
		// throw new FetchException("unknown error at fetching URL: '" + subentry + "'",
		// e);
		// }
	}

	public static void main(String[] args) {
		readPORT();
		if (PORT != 0) {
			try {
				new Socket(LOCAL_HOST, PORT);
				if (debug != 0) {
					System.err.println(ERROR_ALREADY_RUNNING);
					System.exit(-2);
				}
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			}

		}

		UIManager.put("JProgressBar.foreground", Color.yellow);
		UIManager.put("JProgressBar.selectionBackground", Color.red);
		UIManager.put("JProgressBar.selectionForeground", Color.green);
		try {
			UIManager.setLookAndFeel(LOOK_AND_FEEL);

		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}

		boolean nosave = false;
		for (String string : args) {
			if (string.equals("--nosave")) {
				nosave = true;
			} else if (string.equals("--noload")) {
				// noload =true;
				// nosave =true;
			}
		}
		new MainPanel(nosave);
	}

	protected synchronized void saveLocalData() {
		if (!nosave) {
			long time = System.currentTimeMillis();
			try {
				ObjectOutputStream obout = new ObjectOutputStream(new FileOutputStream(new File(FILE_NAME_DATA)));

				obout.writeObject(TEXT_NOTHING + PORT);

				String tmp = autoupdate.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				tmp = playSounds.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				obout.writeObject(new Integer(model.getDa().size()));
				ArrayList<myVidEntry> a = new ArrayList<myVidEntry>(model.getDa());
				for (int i = 0; i < a.size(); i++) {
					obout.writeObject(a.get(i));

				}

				tmp = wantTray.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				tmp = trayNotification.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				tmp = tubename.getText();
				obout.writeObject(tmp);

				tmp = "";
				obout.writeObject(tmp);

				tmp = false + TEXT_NOTHING;
				obout.writeObject(tmp);

				Rectangle desktopBounds;
				GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
				desktopBounds = env.getMaximumWindowBounds();
				desktopBounds.width += frame.getWidth() * 2 - 10;
				desktopBounds.height += frame.getHeight() * 2 - 10;
				desktopBounds.x -= (frame.getWidth() - 5);
				desktopBounds.y -= (frame.getHeight() - 5);
				Rectangle frameBounds;
				if (!desktopBounds.contains(frame.getBounds())) {
					frame.setLocationRelativeTo(null);
					frame.setPreferredSize(new Dimension(1000, 480));
				}
				frameBounds = frame.getBounds();
				obout.writeObject(frameBounds);

				a = new ArrayList<myVidEntry>(model.getBlack());
				obout.writeObject(a);

				tmp = backToFront.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				Integer intvalue = (((Integer) showData.getValue()).intValue());
				obout.writeObject(intvalue);

				tmp = startminimized.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				tmp = startupupdate.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				tmp = showThumb.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				tmp = showfaveThumb.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				a = new ArrayList<myVidEntry>(faveModel.getDa());
				obout.writeObject(a);

				a = new ArrayList<myVidEntry>(faveModel.getBlack());
				obout.writeObject(a);

				tmp = multi.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				obout.writeObject(stats);

				tmp = reversePlaylist.isSelected() + TEXT_NOTHING;
				obout.writeObject(tmp);

				// new version, will be false first time running
				obout.writeObject(Boolean.FALSE.toString());

				obout.close();
			} catch (FileNotFoundException e) {
				update.setText(ERROR_UNKNOWN);
				log(e);
			} catch (IOException e) {
				update.setText(ERROR_LOCALDATA_IO);
				log(e);
			}
			if (System.currentTimeMillis() - time > LONG_TIME) {
				update.setText(TEXT_SAVING_LONG);
			}
		}
	}

	private class CallerThread extends Thread {
		private int cnt = 0;
		private int curr = 0;
		private List<Subscription> allfeeds;
		private YouTube ytserv;
		private Exception ex;

		public CallerThread(List<Subscription> l, YouTube ytserv) {
			allfeeds = l;
			this.ytserv = ytserv;
		}

		public Exception getEx() {
			return ex;
		}

		@Override
		public void run() {
			Subscription subentry;
			for (int j = 0; j < allfeeds.size(); j++) {
				if (updateThread.isInterrupted())
					break;
				subentry = allfeeds.get(j);
				try {
					cnt += printVideoFeed(ytserv, subentry);
				} catch (Exception e) {
					if (e instanceof FetchException
							&& ((FetchException) e).getCause() instanceof ServiceUnavailableException) {
						ex = e;
					} else {
						log(e);
					}
				}
				curr++;
			}
		}

		public synchronized int getcnt() {
			return cnt;
		}

		public synchronized int getcurr() {
			return curr;
		}
	}

	public static <T> List<List<T>> chopped(List<T> list, final int L) {
		List<List<T>> parts = new ArrayList<List<T>>();
		final int N = list.size();
		for (int i = 0; i < N; i += L) {
			parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + L))));
		}
		return parts;
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// do nothing
	}

}
