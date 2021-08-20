package com.icarus.ibrowser.controller;

import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class HomeController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Button btnForward;
    @FXML
    private WebView webView;
    @FXML
    private TextField tfSearch;

    private WebEngine webEngine;
    private WebHistory webHistory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        webHistory = webEngine.getHistory();
        urlChangedListener();
        btnBack.setDisable(true);
        btnForward.setDisable(true);
        webEngine.load("https://www.baidu.com");
    }

    /**
     * 访问地址栏输入的网址
     * Visit the website entered in the address bar
     */
    public void visit() {
        String url = tfSearch.getText();
        // 如果 url 不是合法的网页地址，加上百度搜索前缀
        // Check the access URL is legal, else add to Baidu search prefix
        if (!isURL(url)) {
            if (isURL("https://" + url)) {
                url = "https://" + url;
            } else {
                url = "https://www.baidu.com/s?ie=utf-8&word=" + url;
            }
        }

        webEngine.load(url);
    }

    /**
     * 返回上一个访问的网页
     * Back to the last web page
     */
    @FXML
    public void backward() {
        webHistory.go(-1);
    }

    @FXML
    public void forward() {
        webHistory.go(1);
    }

    @FXML
    public void refresh() {
        webEngine.reload();
    }

    /**
     * 监听回车键触发访问事件
     * Listen for enter key to trigger access event
     */
    @FXML
    public void toVisit(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            visit();
    }

    /**
     * 正则检查地址是否合法
     * Check the address is legal by regex
     */
    private boolean isURL(String url) {
        String regex = "^([Hh][Tt]{2}[Pp][Ss]?):/{2}(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\??(([A-Za-z0-9-~]+\\=?)([A-Za-z0-9-~]*)\\&?)*)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(url).matches();
    }

    /**
     * 监听浏览器网址变化，更新地址栏显示的内容并设置【前进】、【后退】两个按钮可用状态
     * Monitor changes in browsing URLs and update the content displayed in the address bar
     */
    private void urlChangedListener() {
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                tfSearch.setText(webEngine.getLocation());
                int currentIndex = webHistory.getCurrentIndex();
                ObservableList<WebHistory.Entry> webHistoryEntries = webHistory.getEntries();
                btnBack.setDisable(currentIndex == 0);
                btnForward.setDisable(currentIndex == webHistoryEntries.size() - 1);
            }
        });
    }

}
