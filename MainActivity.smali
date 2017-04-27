.class public Lorg/bruxo/gpsconnected/MainActivity;
.super Landroid/app/Activity;
.source "MainActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# static fields
.field private static final PERMISSIONS_REQUEST_GPS:I = 0x1

.field private static final TAG:Ljava/lang/String;

.field private static doneOpenCounter:Z


# instance fields
.field private DEBUG:Z

.field private checkViewsRunnable:Ljava/lang/Runnable;

.field private handler:Landroid/os/Handler;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .prologue
    .line 29
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "GPSConnected"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-class v1, Lorg/bruxo/gpsconnected/MainActivity;

    invoke-virtual {v1}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lorg/bruxo/gpsconnected/MainActivity;->TAG:Ljava/lang/String;

    .line 33
    const/4 v0, 0x0

    sput-boolean v0, Lorg/bruxo/gpsconnected/MainActivity;->doneOpenCounter:Z

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .prologue
    .line 27
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 31
    const/4 v0, 0x0

    iput-boolean v0, p0, Lorg/bruxo/gpsconnected/MainActivity;->DEBUG:Z

    .line 292
    new-instance v0, Lorg/bruxo/gpsconnected/MainActivity$2;

    invoke-direct {v0, p0}, Lorg/bruxo/gpsconnected/MainActivity$2;-><init>(Lorg/bruxo/gpsconnected/MainActivity;)V

    iput-object v0, p0, Lorg/bruxo/gpsconnected/MainActivity;->checkViewsRunnable:Ljava/lang/Runnable;

    return-void
.end method

.method static synthetic access$000(Lorg/bruxo/gpsconnected/MainActivity;)V
    .locals 0
    .param p0, "x0"    # Lorg/bruxo/gpsconnected/MainActivity;

    .prologue
    .line 27
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->checkViews()V

    return-void
.end method

.method static synthetic access$100(Lorg/bruxo/gpsconnected/MainActivity;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lorg/bruxo/gpsconnected/MainActivity;

    .prologue
    .line 27
    iget-object v0, p0, Lorg/bruxo/gpsconnected/MainActivity;->handler:Landroid/os/Handler;

    return-object v0
.end method

.method private checkIfHasWebAccess()Z
    .locals 3

    .prologue
    .line 287
    const-string v2, "connectivity"

    invoke-virtual {p0, v2}, Lorg/bruxo/gpsconnected/MainActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/net/ConnectivityManager;

    .line 288
    .local v1, "connectivityManager":Landroid/net/ConnectivityManager;
    invoke-virtual {v1}, Landroid/net/ConnectivityManager;->getActiveNetworkInfo()Landroid/net/NetworkInfo;

    move-result-object v0

    .line 289
    .local v0, "activeNetworkInfo":Landroid/net/NetworkInfo;
    if-eqz v0, :cond_0

    const/4 v2, 0x1

    :goto_0
    return v2

    :cond_0
    const/4 v2, 0x0

    goto :goto_0
.end method

.method private checkViews()V
    .locals 7

    .prologue
    const v6, 0x7f070002

    const v5, 0x7f070001

    const/4 v4, 0x1

    const/4 v3, 0x0

    .line 197
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->isMyServiceRunning()Z

    move-result v2

    if-eqz v2, :cond_0

    .line 198
    invoke-virtual {p0, v5}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 199
    .local v0, "lockButton":Landroid/view/View;
    invoke-virtual {v0, v3}, Landroid/view/View;->setEnabled(Z)V

    .line 200
    invoke-virtual {p0, v6}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 201
    .local v1, "unlockButton":Landroid/view/View;
    invoke-virtual {v1, v4}, Landroid/view/View;->setEnabled(Z)V

    .line 208
    :goto_0
    return-void

    .line 203
    .end local v0    # "lockButton":Landroid/view/View;
    .end local v1    # "unlockButton":Landroid/view/View;
    :cond_0
    invoke-virtual {p0, v5}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 204
    .restart local v0    # "lockButton":Landroid/view/View;
    invoke-virtual {v0, v4}, Landroid/view/View;->setEnabled(Z)V

    .line 205
    invoke-virtual {p0, v6}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 206
    .restart local v1    # "unlockButton":Landroid/view/View;
    invoke-virtual {v1, v3}, Landroid/view/View;->setEnabled(Z)V

    goto :goto_0
.end method

.method private isMyServiceRunning()Z
    .locals 5

    .prologue
    .line 211
    const-string v2, "activity"

    invoke-virtual {p0, v2}, Lorg/bruxo/gpsconnected/MainActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/ActivityManager;

    .line 212
    .local v0, "manager":Landroid/app/ActivityManager;
    const v2, 0x7fffffff

    invoke-virtual {v0, v2}, Landroid/app/ActivityManager;->getRunningServices(I)Ljava/util/List;

    move-result-object v2

    invoke-interface {v2}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_2

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/app/ActivityManager$RunningServiceInfo;

    .line 213
    .local v1, "service":Landroid/app/ActivityManager$RunningServiceInfo;
    iget-boolean v3, p0, Lorg/bruxo/gpsconnected/MainActivity;->DEBUG:Z

    if-eqz v3, :cond_1

    .line 214
    const-string v3, "DIOGO isMyService"

    iget-object v4, v1, Landroid/app/ActivityManager$RunningServiceInfo;->service:Landroid/content/ComponentName;

    invoke-virtual {v4}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 215
    :cond_1
    const-string v3, "org.bruxo.gpsconnected.GPSService"

    iget-object v4, v1, Landroid/app/ActivityManager$RunningServiceInfo;->service:Landroid/content/ComponentName;

    invoke-virtual {v4}, Landroid/content/ComponentName;->getClassName()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_0

    .line 216
    const/4 v2, 0x1

    .line 219
    .end local v1    # "service":Landroid/app/ActivityManager$RunningServiceInfo;
    :goto_0
    return v2

    :cond_2
    const/4 v2, 0x0

    goto :goto_0
.end method

.method private openAppSettings()V
    .locals 4

    .prologue
    .line 190
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 191
    .local v0, "intent":Landroid/content/Intent;
    const-string v1, "android.settings.APPLICATION_DETAILS_SETTINGS"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    .line 192
    const-string v1, "package"

    invoke-virtual {p0}, Lorg/bruxo/gpsconnected/MainActivity;->getPackageName()Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x0

    invoke-static {v1, v2, v3}, Landroid/net/Uri;->fromParts(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setData(Landroid/net/Uri;)Landroid/content/Intent;

    .line 193
    invoke-virtual {p0, v0}, Lorg/bruxo/gpsconnected/MainActivity;->startActivity(Landroid/content/Intent;)V

    .line 194
    return-void
.end method

.method private startGPSService()V
    .locals 4

    .prologue
    .line 172
    new-instance v2, Landroid/content/Intent;

    const-class v3, Lorg/bruxo/gpsconnected/GPSService;

    invoke-virtual {v3}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0}, Lorg/bruxo/gpsconnected/MainActivity;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v2

    invoke-virtual {p0, v2}, Lorg/bruxo/gpsconnected/MainActivity;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 174
    const v2, 0x7f070001

    invoke-virtual {p0, v2}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 175
    .local v0, "lockButton":Landroid/view/View;
    const/4 v2, 0x0

    invoke-virtual {v0, v2}, Landroid/view/View;->setEnabled(Z)V

    .line 176
    const v2, 0x7f070002

    invoke-virtual {p0, v2}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 177
    .local v1, "unlockButton":Landroid/view/View;
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Landroid/view/View;->setEnabled(Z)V

    .line 178
    return-void
.end method

.method private stopGPSService()V
    .locals 4

    .prologue
    .line 181
    new-instance v2, Landroid/content/Intent;

    const-class v3, Lorg/bruxo/gpsconnected/GPSService;

    invoke-virtual {v3}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0}, Lorg/bruxo/gpsconnected/MainActivity;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v2

    invoke-virtual {p0, v2}, Lorg/bruxo/gpsconnected/MainActivity;->stopService(Landroid/content/Intent;)Z

    .line 183
    const v2, 0x7f070001

    invoke-virtual {p0, v2}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 184
    .local v0, "lockButton":Landroid/view/View;
    const/4 v2, 0x1

    invoke-virtual {v0, v2}, Landroid/view/View;->setEnabled(Z)V

    .line 185
    const v2, 0x7f070002

    invoke-virtual {p0, v2}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 186
    .local v1, "unlockButton":Landroid/view/View;
    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/view/View;->setEnabled(Z)V

    .line 187
    return-void
.end method

.method private updateOpenCounter()V
    .locals 10

    .prologue
    const/4 v9, 0x0

    .line 223
    iget-boolean v7, p0, Lorg/bruxo/gpsconnected/MainActivity;->DEBUG:Z

    if-eqz v7, :cond_0

    .line 224
    sget-object v7, Lorg/bruxo/gpsconnected/MainActivity;->TAG:Ljava/lang/String;

    const-string v8, "updateOpenCounter"

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 226
    :cond_0
    const-string v7, "org.bruxo.gpsconnected"

    invoke-virtual {p0, v7, v9}, Lorg/bruxo/gpsconnected/MainActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v2

    .line 228
    .local v2, "prefs":Landroid/content/SharedPreferences;
    const-string v7, "timesOpened"

    invoke-interface {v2, v7, v9}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v6

    .line 229
    .local v6, "timesOpened":I
    const-string v7, "shownRateApp"

    invoke-interface {v2, v7, v9}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v4

    .line 230
    .local v4, "shownRateApp":Z
    const-string v7, "shownBuyPro"

    invoke-interface {v2, v7, v9}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    .line 231
    .local v3, "shownBuyPro":Z
    const-string v7, "thanksBuyPro"

    invoke-interface {v2, v7, v9}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v5

    .line 235
    .local v5, "thanksBuyPro":Z
    :try_start_0
    invoke-virtual {p0}, Lorg/bruxo/gpsconnected/MainActivity;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v7

    const-string v8, "org.bruxo.gpsconnectedpro"

    const/4 v9, 0x0

    invoke-virtual {v7, v8, v9}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v1

    .line 240
    .local v1, "hasProVersion":Landroid/content/pm/ApplicationInfo;
    :goto_0
    add-int/lit8 v6, v6, 0x1

    .line 241
    if-nez v4, :cond_2

    .line 242
    iget-boolean v7, p0, Lorg/bruxo/gpsconnected/MainActivity;->DEBUG:Z

    if-eqz v7, :cond_1

    .line 243
    sget-object v7, Lorg/bruxo/gpsconnected/MainActivity;->TAG:Ljava/lang/String;

    const-string v8, "never shown rate app"

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 246
    :cond_1
    const/16 v7, 0xa

    if-le v6, v7, :cond_2

    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->checkIfHasWebAccess()Z

    move-result v7

    if-eqz v7, :cond_2

    .line 247
    invoke-static {p0}, Lorg/bruxo/gpsconnected/Dialogs;->rateApp(Landroid/app/Activity;)V

    .line 248
    const/4 v4, 0x1

    .line 251
    :cond_2
    if-nez v3, :cond_4

    .line 252
    iget-boolean v7, p0, Lorg/bruxo/gpsconnected/MainActivity;->DEBUG:Z

    if-eqz v7, :cond_3

    .line 253
    sget-object v7, Lorg/bruxo/gpsconnected/MainActivity;->TAG:Ljava/lang/String;

    const-string v8, "never shown buy pro"

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 255
    :cond_3
    if-eqz v1, :cond_7

    .line 256
    const/4 v3, 0x1

    .line 265
    :cond_4
    :goto_1
    if-nez v5, :cond_6

    .line 266
    iget-boolean v7, p0, Lorg/bruxo/gpsconnected/MainActivity;->DEBUG:Z

    if-eqz v7, :cond_5

    .line 267
    sget-object v7, Lorg/bruxo/gpsconnected/MainActivity;->TAG:Ljava/lang/String;

    const-string v8, "never thanks buy pro"

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 269
    :cond_5
    if-eqz v1, :cond_6

    .line 270
    invoke-static {p0}, Lorg/bruxo/gpsconnected/Dialogs;->thanksBuyPro(Landroid/app/Activity;)V

    .line 271
    const/4 v5, 0x1

    .line 275
    :cond_6
    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v7

    const-string v8, "timesOpened"

    invoke-interface {v7, v8, v6}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    move-result-object v7

    invoke-interface {v7}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 276
    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v7

    const-string v8, "shownRateApp"

    invoke-interface {v7, v8, v4}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v7

    invoke-interface {v7}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 277
    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v7

    const-string v8, "shownBuyPro"

    invoke-interface {v7, v8, v3}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v7

    invoke-interface {v7}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 278
    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v7

    const-string v8, "thanksBuyPro"

    invoke-interface {v7, v8, v5}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v7

    invoke-interface {v7}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 279
    return-void

    .line 236
    .end local v1    # "hasProVersion":Landroid/content/pm/ApplicationInfo;
    :catch_0
    move-exception v0

    .line 237
    .local v0, "e":Ljava/lang/Exception;
    const/4 v1, 0x0

    .restart local v1    # "hasProVersion":Landroid/content/pm/ApplicationInfo;
    goto :goto_0

    .line 259
    .end local v0    # "e":Ljava/lang/Exception;
    :cond_7
    const/16 v7, 0x14

    if-le v6, v7, :cond_4

    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->checkIfHasWebAccess()Z

    move-result v7

    if-eqz v7, :cond_4

    .line 260
    invoke-static {p0}, Lorg/bruxo/gpsconnected/Dialogs;->buyPro(Landroid/app/Activity;)V

    .line 261
    const/4 v3, 0x1

    goto :goto_1
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1
    .param p1, "v"    # Landroid/view/View;

    .prologue
    .line 115
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    packed-switch v0, :pswitch_data_0

    .line 126
    :goto_0
    :pswitch_0
    return-void

    .line 117
    :pswitch_1
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->startGPSService()V

    goto :goto_0

    .line 120
    :pswitch_2
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->stopGPSService()V

    goto :goto_0

    .line 123
    :pswitch_3
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->openAppSettings()V

    goto :goto_0

    .line 115
    :pswitch_data_0
    .packed-switch 0x7f070001
        :pswitch_1
        :pswitch_2
        :pswitch_0
        :pswitch_0
        :pswitch_3
    .end packed-switch
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 5
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 41
    invoke-static {p0}, Lorg/bruxo/gpsconnected/Reporter;->initContext(Landroid/app/Activity;)V
    iget-boolean v3, p0, Lorg/bruxo/gpsconnected/MainActivity;->DEBUG:Z
	
    if-eqz v3, :cond_0

    .line 42
    sget-object v3, Lorg/bruxo/gpsconnected/MainActivity;->TAG:Ljava/lang/String;

    const-string v4, "onCreate"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 44
    :cond_0
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V


    .line 45
    const/high16 v3, 0x7f030000

    invoke-virtual {p0, v3}, Lorg/bruxo/gpsconnected/MainActivity;->setContentView(I)V

    .line 47
    new-instance v3, Landroid/os/Handler;

    invoke-direct {v3}, Landroid/os/Handler;-><init>()V

    iput-object v3, p0, Lorg/bruxo/gpsconnected/MainActivity;->handler:Landroid/os/Handler;

    .line 50
    const v3, 0x7f070001

    invoke-virtual {p0, v3}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 51
    .local v0, "lockButton":Landroid/view/View;
    invoke-virtual {v0, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 52
    const v3, 0x7f070002

    invoke-virtual {p0, v3}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .line 53
    .local v2, "unlockButton":Landroid/view/View;
    invoke-virtual {v2, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 54
    const v3, 0x7f070005

    invoke-virtual {p0, v3}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 55
    .local v1, "permissionButton":Landroid/view/View;
    invoke-virtual {v1, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 57
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->checkViews()V

    .line 59
    sget-boolean v3, Lorg/bruxo/gpsconnected/MainActivity;->doneOpenCounter:Z

    if-nez v3, :cond_1

    .line 60
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->updateOpenCounter()V

    .line 62
    :cond_1
    return-void
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 2
    .param p1, "menu"    # Landroid/view/Menu;

    .prologue
    .line 131
    invoke-virtual {p0}, Lorg/bruxo/gpsconnected/MainActivity;->getMenuInflater()Landroid/view/MenuInflater;

    move-result-object v0

    const/high16 v1, 0x7f060000

    invoke-virtual {v0, v1, p1}, Landroid/view/MenuInflater;->inflate(ILandroid/view/Menu;)V

    .line 132
    const/4 v0, 0x1

    return v0
.end method

.method public onOptionsItemSelected(Landroid/view/MenuItem;)Z
    .locals 9
    .param p1, "item"    # Landroid/view/MenuItem;

    .prologue
    const v8, 0x7f040001

    const/4 v4, 0x1

    const/4 v5, 0x0

    .line 137
    invoke-interface {p1}, Landroid/view/MenuItem;->getItemId()I

    move-result v6

    packed-switch v6, :pswitch_data_0

    move v4, v5

    .line 168
    :goto_0
    return v4

    .line 141
    :pswitch_0
    :try_start_0
    invoke-virtual {p0}, Lorg/bruxo/gpsconnected/MainActivity;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v5

    const-string v6, "org.bruxo.gpsconnectedpro"

    const/4 v7, 0x0

    invoke-virtual {v5, v6, v7}, Landroid/content/pm/PackageManager;->getApplicationInfo(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v3

    .line 146
    .local v3, "info":Landroid/content/pm/ApplicationInfo;
    :goto_1
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-direct {v1, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 148
    .local v1, "builder":Landroid/app/AlertDialog$Builder;
    if-eqz v3, :cond_0

    .line 149
    const v5, 0x7f040004

    invoke-virtual {v1, v5}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    .line 150
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const v6, 0x7f040002

    invoke-virtual {p0, v6}, Lorg/bruxo/gpsconnected/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "\n"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {p0, v8}, Lorg/bruxo/gpsconnected/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v1, v5}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 156
    :goto_2
    invoke-virtual {v1, v4}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    .line 157
    const v5, 0x7f04000a

    invoke-virtual {p0, v5}, Lorg/bruxo/gpsconnected/MainActivity;->getString(I)Ljava/lang/String;

    move-result-object v5

    new-instance v6, Lorg/bruxo/gpsconnected/MainActivity$1;

    invoke-direct {v6, p0}, Lorg/bruxo/gpsconnected/MainActivity$1;-><init>(Lorg/bruxo/gpsconnected/MainActivity;)V

    invoke-virtual {v1, v5, v6}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 162
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 163
    .local v0, "aboutDialog":Landroid/app/AlertDialog;
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    goto :goto_0

    .line 142
    .end local v0    # "aboutDialog":Landroid/app/AlertDialog;
    .end local v1    # "builder":Landroid/app/AlertDialog$Builder;
    .end local v3    # "info":Landroid/content/pm/ApplicationInfo;
    :catch_0
    move-exception v2

    .line 143
    .local v2, "e":Ljava/lang/Exception;
    const/4 v3, 0x0

    .restart local v3    # "info":Landroid/content/pm/ApplicationInfo;
    goto :goto_1

    .line 152
    .end local v2    # "e":Ljava/lang/Exception;
    .restart local v1    # "builder":Landroid/app/AlertDialog$Builder;
    :cond_0
    const v5, 0x7f040003

    invoke-virtual {v1, v5}, Landroid/app/AlertDialog$Builder;->setTitle(I)Landroid/app/AlertDialog$Builder;

    .line 153
    invoke-virtual {v1, v8}, Landroid/app/AlertDialog$Builder;->setMessage(I)Landroid/app/AlertDialog$Builder;

    goto :goto_2

    .line 137
    :pswitch_data_0
    .packed-switch 0x7f070006
        :pswitch_0
    .end packed-switch
.end method

.method public onRequestPermissionsResult(I[Ljava/lang/String;[I)V
    .locals 4
    .param p1, "requestCode"    # I
    .param p2, "permissions"    # [Ljava/lang/String;
    .param p3, "grantResults"    # [I

    .prologue
    const/4 v1, 0x0

    .line 66
    packed-switch p1, :pswitch_data_0

    .line 75
    :goto_0
    return-void

    .line 68
    :pswitch_0
    aget v0, p3, v1

    if-nez v0, :cond_0

    .line 69
    iget-object v0, p0, Lorg/bruxo/gpsconnected/MainActivity;->handler:Landroid/os/Handler;

    iget-object v1, p0, Lorg/bruxo/gpsconnected/MainActivity;->checkViewsRunnable:Ljava/lang/Runnable;

    const-wide/16 v2, 0x3e8

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    .line 71
    :cond_0
    const v0, 0x7f070003

    invoke-virtual {p0, v0}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    goto :goto_0

    .line 66
    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
    .end packed-switch
.end method

.method protected onStart()V
    .locals 8

    .prologue
    const-wide/16 v6, 0x3e8

    const v4, 0x7f070003

    const/4 v3, 0x1

    const/4 v2, 0x0

    .line 79
    invoke-super {p0}, Landroid/app/Activity;->onStart()V

    .line 81
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->checkViews()V

    .line 83
    invoke-virtual {p0, v4}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 85
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x17

    if-lt v0, v1, :cond_3

    .line 86
    const-string v0, "android.permission.ACCESS_FINE_LOCATION"

    invoke-virtual {p0, v0}, Lorg/bruxo/gpsconnected/MainActivity;->checkSelfPermission(Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_1

    const-string v0, "android.permission.ACCESS_COARSE_LOCATION"

    invoke-virtual {p0, v0}, Lorg/bruxo/gpsconnected/MainActivity;->checkSelfPermission(Ljava/lang/String;)I

    move-result v0

    if-eqz v0, :cond_1

    .line 87
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->isMyServiceRunning()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 88
    invoke-direct {p0}, Lorg/bruxo/gpsconnected/MainActivity;->stopGPSService()V

    .line 91
    :cond_0
    const/4 v0, 0x2

    new-array v0, v0, [Ljava/lang/String;

    const-string v1, "android.permission.ACCESS_FINE_LOCATION"

    aput-object v1, v0, v2

    const-string v1, "android.permission.ACCESS_COARSE_LOCATION"

    aput-object v1, v0, v3

    invoke-virtual {p0, v0, v3}, Lorg/bruxo/gpsconnected/MainActivity;->requestPermissions([Ljava/lang/String;I)V

    .line 104
    :goto_0
    return-void

    .line 95
    :cond_1
    const-string v0, "android.permission.ACCESS_FINE_LOCATION"

    invoke-virtual {p0, v0}, Lorg/bruxo/gpsconnected/MainActivity;->shouldShowRequestPermissionRationale(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 96
    invoke-virtual {p0, v4}, Lorg/bruxo/gpsconnected/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    invoke-virtual {v0, v2}, Landroid/view/View;->setVisibility(I)V

    goto :goto_0

    .line 98
    :cond_2
    iget-object v0, p0, Lorg/bruxo/gpsconnected/MainActivity;->handler:Landroid/os/Handler;

    iget-object v1, p0, Lorg/bruxo/gpsconnected/MainActivity;->checkViewsRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1, v6, v7}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    .line 102
    :cond_3
    iget-object v0, p0, Lorg/bruxo/gpsconnected/MainActivity;->handler:Landroid/os/Handler;

    iget-object v1, p0, Lorg/bruxo/gpsconnected/MainActivity;->checkViewsRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1, v6, v7}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0
.end method

.method protected onStop()V
    .locals 2

    .prologue
    .line 108
    invoke-super {p0}, Landroid/app/Activity;->onStop()V

    .line 110
    iget-object v0, p0, Lorg/bruxo/gpsconnected/MainActivity;->handler:Landroid/os/Handler;

    iget-object v1, p0, Lorg/bruxo/gpsconnected/MainActivity;->checkViewsRunnable:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 111
    return-void
.end method
