MDIR=src/net/driftingsouls/ds2/server
CDIR=../ds2j-opensource/$MDIR

mkdir -p $MDIR/entities/ && cp $CDIR/entities/JumpNode.java "$_"
mkdir -p $MDIR/config/ && cp $CDIR/config/StarSystem.java "$_"
mkdir -p $MDIR && cp $CDIR/Location.java "$_"
mkdir -p $MDIR && cp $CDIR/Locatable.java "$_"

