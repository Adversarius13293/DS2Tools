MDIR=src/net/driftingsouls/ds2/server
CDIR=../ds2j-opensource/$MDIR

mkdir -p $MDIR/entities/ && cp -n $CDIR/entities/JumpNode.java "$_"
mkdir -p $MDIR/entities/ && cp -n $CDIR/entities/Weapon.java "$_"
mkdir -p $MDIR/entities/ && cp -n $CDIR/entities/Forschung.java "$_"
mkdir -p $MDIR/cargo/ && cp -n $CDIR/cargo/Cargo.java "$_"
mkdir -p $MDIR/cargo/ && cp -n $CDIR/cargo/ItemCargoEntry.java "$_"
mkdir -p $MDIR/cargo/ && cp -n $CDIR/cargo/ResourceID.java "$_"
mkdir -p $MDIR/cargo/ && cp -n $CDIR/cargo/ItemID.java "$_"
mkdir -p $MDIR/config/ && cp -n $CDIR/config/StarSystem.java "$_"
mkdir -p $MDIR/config/ && cp -n $CDIR/config/Weapons.java "$_"
mkdir -p $MDIR/config/items/ && cp -n $CDIR/config/items/Item.java "$_"
mkdir -p $MDIR/framework/ && cp -n $CDIR/framework/Common.java "$_"
mkdir -p $MDIR/ships/ && cp -n $CDIR/ships/ShipType.java "$_"
mkdir -p $MDIR/ships/ && cp -n $CDIR/ships/ShipClasses.java "$_"
mkdir -p $MDIR/ships/ && cp -n $CDIR/ships/ShipTypeData.java "$_"
mkdir -p $MDIR/ships/ && cp -n $CDIR/ships/ShipTypeFlag.java "$_"
mkdir -p $MDIR/ships/ && cp -n $CDIR/ships/ShipBaubar.java "$_"
mkdir -p $MDIR/ships/ && cp -n $CDIR/ships/JumpNodeRouter.java "$_"
mkdir -p $MDIR && cp -n $CDIR/Location.java "$_"
mkdir -p $MDIR && cp -n $CDIR/Locatable.java "$_"

