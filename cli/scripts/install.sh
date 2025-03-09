SCRIPT=$(realpath "$0")
SCRIPTPATH=$(dirname "$SCRIPT")

mkdir -p /usr/share/kayjam
cp $SCRIPTPATH/../build/libs/*.jar /usr/share/kayjam/kayjam-cli.jar
cp $SCRIPTPATH/kayjam /usr/share/kayjam/kayjam
chmod +x /usr/share/kayjam/kayjam
ln -s /usr/share/kayjam/kayjam /usr/bin