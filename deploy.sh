#! /bin/sh
set -eux

H=root@games.alexecollins.com

ssh $H "[ -e /var/gf ] || mkdir /var/gf"
ssh $H "adduser --system gf || true"

rsync -avz --progress target/gf-1.0.0-SNAPSHOT.jar $H:/var/gf/gf.jar

ssh $H "chmod +x /var/gf/gf.jar && ln -sf /var/gf/gf.jar /etc/init.d/gf && chown gf /etc/init.d/gf && systemctl daemon-reload && systemctl start gf && systemctl restart gf"
