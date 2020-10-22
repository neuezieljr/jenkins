import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import com.sonyericsson.hudson.plugins.gerrit.trigger.GerritServer;

if ( Jenkins.instance.pluginManager.activePlugins.find { it.shortName == "gerrit-trigger" } != null ){
  println("Setting gerrit-trigger server plugin");

  def gerritPlugin = Jenkins.instance.getPlugin(com.sonyericsson.hudson.plugins.gerrit.trigger.PluginImpl.class);

  gerritPlugin.getPluginConfig().setNumberOfReceivingWorkerThreads(3);
  gerritPlugin.getPluginConfig().setNumberOfSendingWorkerThreads(1);

  def serverName = "code.engineering.redhat.com";
  GerritServer server = new GerritServer(serverName);

  def config = server.getConfig();
  def triggerConfig = [
    'gerritHostName': "code.engineering.redhat.com",
    'gerritSshPort': 29418,
    'gerritUserName': "cci-errata-jenkins",
    'gerritFrontEndUrl': "https://code.engineering.redhat.com/",
    'gerritAuthKeyFile': "/run/secrets/casc-secret/CCI_ERRATA_JENKINS_PRIV_KEY"
  ];

  config.setValues(JSONObject.fromObject(triggerConfig));
  server.setConfig(config);

  // avoid duplicate servers on the server list;
  if ( gerritPlugin.containsServer(serverName) ) {
    gerritPlugin.removeServer(gerritPlugin.getServer(serverName));
  }

  gerritPlugin.addServer(server);
  server.start();
  server.startConnection();
  println("Setting ${serverName} completed");
}
