mkdir -p /usr/share/kayjam
cp ../build/libs/*.jar /usr/share/kayjam/kayjam-cli.jar
cp ./kayjam /usr/share/kayjam/kayjam
chmod +x /usr/share/kayjam/kayjam
ln -s /usr/share/kayjam/kayjam /usr/bin