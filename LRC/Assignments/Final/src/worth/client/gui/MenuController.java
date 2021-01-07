package worth.client.gui;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import worth.client.Const;
import worth.client.Utils;
import worth.client.chat.Chat;
import worth.client.tcp.Commands;
import worth.client.tcp.Response;
import worth.client.users.Users;
import worth.common.Status;

public class MenuController {
    private Chat chat;
    private String username;
    private String project;

    public void init(Scene scene, String username) {
        this.username = username;
        scene.getWindow().setWidth(container.getPrefWidth());
        scene.getWindow().setHeight(container.getPrefHeight());

        lvUsers.setSelectionModel(new NoSelectionModel<>());
        lvUsers.setFocusTraversable(false);
        lvChat.setSelectionModel(new NoSelectionModel<>());
        lvChat.setFocusTraversable(false);
        lvPMembers.setSelectionModel(new NoSelectionModel<>());
        lvPMembers.setFocusTraversable(false);

        Users.registerListView().addListener((ListChangeListener<Text>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    Platform.runLater(() ->  
                        lvUsers.getItems().addAll(c.getFrom(), c.getAddedSubList()));
                } 
                if (c.wasRemoved()) {
                    Platform.runLater(() ->
                        lvUsers.getItems().removeAll(c.getRemoved()));
                }
            }
        });
        Users.refreshList();
        refreshProjects();
        lvProjects.getSelectionModel().selectedItemProperty().addListener((p, o, n) -> {
            showProject(n);
        });

        initTableView(tvTD, tvTDc, tvTDd);
        initTableView(tvIP, tvIPc, tvIPd);
        initTableView(tvTR, tvTRc, tvTRd);
        initTableView(tvDN, tvDNc, tvDNd);
    }

    private void initTableView(TableView<Card> tv, TableColumn<Card, String> tvC, TableColumn<Card, String> tvD) {
        tvC.setCellValueFactory(new PropertyValueFactory<>("card"));
        tvD.setCellValueFactory(new PropertyValueFactory<>("description"));
        tv.getSelectionModel().selectedItemProperty().addListener((p, o, n) -> {
            if(n == null) return;
            if(!showCardDialog(n, statusFromTV(tv))) Platform.runLater(() -> tv.getSelectionModel().clearSelection());
            Platform.runLater(() -> tv.getItems().remove(n));
            addCardToTab(project, n.getCard());
        });
        tv.setOnMouseClicked(e -> {
            tv.getSelectionModel().clearSelection();
            // Platform.runLater(() -> tv.getSelectionModel().clearSelection());
        });
    }

    private boolean showCardDialog(Card c, Status from) {
        if(c == null) return false;
        String card = c.getCard();
        String description = c.getDescription();
        Response historyR = Commands.getCardHistory(project, card);
        if(!historyR.getStatus()) return false;
        List<Text> historyT = historyR.getMessages().stream().map(s -> new Text(Status.getListString().get(Integer.valueOf(s)))).collect(Collectors.toList());

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Card history");
        dialog.setHeaderText("The history of movements of this card is:");
        dialog.setResizable(true);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setPrefWidth(Const.DIALOG_WIDTH);
        dialogPane.setPrefHeight(240);
        ButtonType move = new ButtonType("Move");
        dialogPane.getButtonTypes().addAll(move, ButtonType.CANCEL);
        ListView<Text> lvHistory = new ListView<>();
        lvHistory.setSelectionModel(new NoSelectionModel<>());
        lvHistory.getItems().addAll(historyT);
        dialogPane.setContent(lvHistory);
        dialog.setResultConverter(b -> {
            if(b.equals(move)) {
                return true;
            }
            return null;
        });
        if(!dialog.showAndWait().orElse(false)) return false;
        ChoiceDialog<String> d = new ChoiceDialog<>(Status.getListString().get(0), Status.getListString());
        d.setTitle(card);
        d.setHeaderText(description);
        d.setContentText("Move to: ");
        d.setResizable(true);
        d.getDialogPane().setPrefWidth(Const.DIALOG_WIDTH);
        Status to;
        try {
            to = Status.fromString(d.showAndWait().orElseThrow());
        } catch (NoSuchElementException e) {
            return false;
        }
        Response moveR = Commands.moveCard(project, card, from, to);
        if(!moveR.getStatus()) {
            Utils.ErrorAlert("Cannot move the card to the specified list").showAndWait();
            return false;
        }
        Utils.InfoAlert("Done").show();
        return true;
    } 

    // Cards lists

    @FXML
    protected TableView<Card> tvTD = new TableView<>();
    @FXML
    protected TableColumn<Card, String> tvTDc = new TableColumn<>();
    @FXML
    protected TableColumn<Card, String> tvTDd = new TableColumn<>();
    @FXML
    protected TableView<Card> tvIP = new TableView<>();
    @FXML
    protected TableColumn<Card, String> tvIPc = new TableColumn<>();
    @FXML
    protected TableColumn<Card, String> tvIPd = new TableColumn<>();
    @FXML
    protected TableView<Card> tvTR = new TableView<>();
    @FXML
    protected TableColumn<Card, String> tvTRc = new TableColumn<>();
    @FXML
    protected TableColumn<Card, String> tvTRd = new TableColumn<>();
    @FXML
    protected TableView<Card> tvDN = new TableView<>();
    @FXML
    protected TableColumn<Card, String> tvDNc = new TableColumn<>();
    @FXML
    protected TableColumn<Card, String> tvDNd = new TableColumn<>();


    // Menu Bar items and ActionListeners
    @FXML
    protected MenuItem refreshProject = new MenuItem();
    @FXML
    protected MenuItem addMember = new MenuItem();
    @FXML
    protected MenuItem addCard = new MenuItem();
    @FXML
    protected MenuItem delProject = new MenuItem();
    @FXML
    private void refreshProjectAction(Event e) {
        showProject(lvProjects.getSelectionModel().getSelectedItem());
    }
    @FXML
    private void addProjectAction(Event e) {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Add project");
        d.setHeaderText("Enter the name for the project");
        d.setContentText("Project:");
        d.setResizable(true);
        d.getDialogPane().setPrefWidth(Const.DIALOG_WIDTH);
        String project = d.showAndWait().orElse("");
        Response r = Commands.createProject(project);
        if(!r.getStatus()) {
            Utils.ErrorAlert("Cannot create project").showAndWait();
            return;
        }
        refreshProjects();
    }
    @FXML
    private void addMemberAction(Event e) {
        List<String> members = lvPMembers.getItems().stream().map(m -> m.getText()).collect(Collectors.toList());
        List<String> notMembers = Users.listUsers().stream().filter(u -> !members.contains(u)).collect(Collectors.toList());
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", notMembers);
        dialog.setTitle("Add member");
        dialog.setHeaderText("Select the user");
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefWidth(Const.DIALOG_WIDTH);
        dialog.showAndWait().ifPresent(u -> {
            Response addR = Commands.addMember(project, u);
            if(!addR.getStatus()) {
                Utils.ErrorAlert("Cannot add member " + u).show();
                return;
            }
            refresMembers();
        });
    }
    @FXML
    private void addCardAction(Event e) {
        Dialog<Card> dialog = new Dialog<>();
        dialog.setTitle("Add card");
        dialog.setHeaderText("Please specify...");
        dialog.setResizable(true);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setPrefWidth(Const.DIALOG_WIDTH);
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField tfCard = new TextField();
        tfCard.setPromptText("Card name");
        TextField tfDescription = new TextField();
        tfDescription.setPromptText("Card description");
        dialogPane.setContent(new VBox(8, tfCard, tfDescription));
        dialog.setResultConverter(b -> {
            if(b == ButtonType.OK) {
                return new Card(tfCard.getText(), tfDescription.getText());
            }
            return null;
        });
        dialog.showAndWait().ifPresent(c -> {
            Response addR = Commands.addCard(project, c.getCard(), c.getDescription());
            if(!addR.getStatus()) {
                Utils.ErrorAlert("Cannot add this card").show();;
                return;
            }
            addCardToTab(project, c.getCard());
        });
    }
    @FXML
    private void delProjectAction(Event e) {
        Response delR = Commands.deleteProject(project);
        if(!delR.getStatus()) {
            Utils.ErrorAlert("Cannot delete this project").show();
            return;
        }
        Utils.InfoAlert("Done").show();
        Platform.runLater(() -> {
            lvProjects.getSelectionModel().clearSelection();
            showProject(null);
            refreshProjects();
        });
    }

    // Chat

    @FXML
    protected ListView<Text> lvChat = new ListView<>();
    @FXML
    protected TextField tfChat = new TextField();
    @FXML
    protected ListView<Text> lvPMembers = new ListView<>();
    @FXML
    private void tfChatKey(KeyEvent e) {
        if(e.getCode().equals(KeyCode.ENTER)) {
            if(chat == null) return;
            chat.send(username, tfChat.getText());
            tfChat.clear();
        }
    }
    @FXML
    private void sendButtonAction(Event e) {
        if(chat == null) return;
        chat.send(username, tfChat.getText());
        tfChat.clear();
    }


    // Common 
    @FXML
    protected VBox container = new VBox();
    @FXML
    protected ListView<Text> lvUsers = new ListView<>();
    @FXML
    protected ListView<Text> lvProjects = new ListView<>();
    @FXML
    protected TabPane tabPane = new TabPane();
    
    @FXML
    protected void quitAction(Event e) {
        Commands.logout();
        System.exit(0);
    }

    private void refreshProjects() {
        Response r = Commands.listProjects();
        if(!r.getStatus()) Utils.showErrorAndExit("Cannot retreive the list of projects");
        lvProjects.getItems().clear();
        r.getMessages().stream().forEach(p -> lvProjects.getItems().add(new Text(p)));
    }

    private void showProject(Text t) {
        // Close previous chat
        if(chat != null) chat.close();
        lvChat.getItems().clear();
        // Disable unusable features
        if(t == null) {
            tabPane.setDisable(true);
            refreshProject.setDisable(true);
            addMember.setDisable(true);
            addCard.setDisable(true);
            delProject.setDisable(true);
            return;
        }
        tabPane.setDisable(false);
        refreshProject.setDisable(false);
        addMember.setDisable(false);
        addCard.setDisable(false);
        delProject.setDisable(false);

        project = t.getText();

        // Retreive project's members
        refresMembers();

        // Retreive cards
        Response cardsR = Commands.showCards(project);
        if(!cardsR.getStatus()) {
            Utils.ErrorAlert("Cannot retreive the project's information").show();
            Platform.runLater(() -> {
                lvProjects.getSelectionModel().clearSelection();
                refreshProjects();
                showProject(null);
            });
            return;
        }
        tvTD.getItems().clear();
        tvIP.getItems().clear();
        tvTR.getItems().clear();
        tvDN.getItems().clear();
        cardsR.getMessages().stream().forEach(card -> {
            addCardToTab(project, card);
        });


        chat = new Chat(project);
        chat.getMessages().addListener((ListChangeListener<Text>) c -> {

            while (c.next()) {
                if (c.wasAdded()) {
                    Platform.runLater(() ->  
                        lvChat.getItems().addAll(c.getFrom(), c.getAddedSubList()));
                } 
                if (c.wasRemoved()) {
                    Platform.runLater(() ->
                        lvChat.getItems().removeAll(c.getRemoved()));
                }
            }

            Platform.runLater(() -> lvChat.scrollTo(lvChat.getItems().size()-1));
        });


        
    }

    private void refresMembers() {
        Response membersR = Commands.showMembers(project);
        if(!membersR.getStatus()) {
            Utils.showErrorAndExit("Cannot retreive the project's information");
        }
        lvPMembers.getItems().clear();
        membersR.getMessages().stream().forEach(m -> {
            lvPMembers.getItems().add(new Text(m));
        });
    }

    private void addCardToTab(String project, String card) {
        Response cardInfoR = Commands.showCard(project, card);
        if(!cardInfoR.getStatus()) return;
        List<String> info  = cardInfoR.getMessages();
        TableView<Card> tv = tvFromStatus(info.get(2));
        String description = info.get(1);
        tv.getItems().add(new Card(card, description));
    }

    private TableView<Card> tvFromStatus(String status) {
        if(status.equals(new String(Status.TODO.toString()))) return tvTD;
        if(status.equals(new String(Status.INPROGRESS.toString()))) return tvIP;
        if(status.equals(new String(Status.TOBEREVISED.toString()))) return tvTR;
        return tvDN;
    }

    private Status statusFromTV(TableView<Card> tv) {
        if(tv.equals(tvTD)) return Status.TODO;
        if(tv.equals(tvIP)) return Status.INPROGRESS;
        if(tv.equals(tvTR)) return Status.TOBEREVISED;
        return Status.DONE;
    }
}
