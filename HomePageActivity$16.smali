.class Lcom/quikr/old/HomePageActivity$16;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/quikr/old/HomePageActivity;->getLastKnownLocation(Landroid/content/Context;Landroid/location/LocationListener;)Landroid/location/Location;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field private final synthetic val$activity:Lcom/quikr/old/HomePageActivity;

.field private final synthetic val$isNetworkEnabled:Z

.field private final synthetic val$mLocationManager:Landroid/location/LocationManager;


# direct methods
.method constructor <init>(ZLandroid/location/LocationManager;Lcom/quikr/old/HomePageActivity;)V
    .locals 0

    iput-boolean p1, p0, Lcom/quikr/old/HomePageActivity$16;->val$isNetworkEnabled:Z

    iput-object p2, p0, Lcom/quikr/old/HomePageActivity$16;->val$mLocationManager:Landroid/location/LocationManager;

    iput-object p3, p0, Lcom/quikr/old/HomePageActivity$16;->val$activity:Lcom/quikr/old/HomePageActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 6

    iget-boolean v0, p0, Lcom/quikr/old/HomePageActivity$16;->val$isNetworkEnabled:Z

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/quikr/old/HomePageActivity$16;->val$mLocationManager:Landroid/location/LocationManager;

    const-string v1, "network"

    const-wide/16 v2, 0xa

    const v4, 0x476a6000

    iget-object v5, p0, Lcom/quikr/old/HomePageActivity$16;->val$activity:Lcom/quikr/old/HomePageActivity;

    invoke-virtual/range {v0 .. v5}, Landroid/location/LocationManager;->requestLocationUpdates(Ljava/lang/String;JFLandroid/location/LocationListener;)V

    iget-object v0, p0, Lcom/quikr/old/HomePageActivity$16;->val$mLocationManager:Landroid/location/LocationManager;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/quikr/old/HomePageActivity$16;->val$activity:Lcom/quikr/old/HomePageActivity;

    iget-object v1, p0, Lcom/quikr/old/HomePageActivity$16;->val$mLocationManager:Landroid/location/LocationManager;

    const-string v2, "network"

invoke-static {}, Lcom/quikr/old/Reporter;->report()V
    invoke-virtual {v1, v2}, Landroid/location/LocationManager;->getLastKnownLocation(Ljava/lang/String;)Landroid/location/Location;

    move-result-object v1

    iput-object v1, v0, Lcom/quikr/old/HomePageActivity;->bestLocationProvider:Landroid/location/Location;

    :cond_0
    return-void
.end method
