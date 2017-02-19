#! /bin/sh
set -eux

ssh games.alexecollins.com "[ -e /var/gf ] || mkdir /var/gf"

rsync -avz target/gf-1.0.0-SNAPSHOT.jar games.alexecollins.com:/var/gf/gf.jar

ssh games.alexecollins.com "ln -sf /var/gf/gf.jar /etc/init.d/gf && systemctl daemon-reload && systemctl start gf"
