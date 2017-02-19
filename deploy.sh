#! /bin/sh
set -eux

ssh games.alexecollins.com "[ -e /var/gf ] || mkdir /var/gf"
ssh games.alexecollins.com "adduser --system gf || true"

rsync -avz target/gf-1.0.0-SNAPSHOT.jar games.alexecollins.com:/var/gf/gf.jar

ssh games.alexecollins.com "ln -sf /var/gf/gf.jar /etc/init.d/gf && chown gf /etc/init.d/gf && systemctl daemon-reload && systemctl start gf && systemctl restart gf"
