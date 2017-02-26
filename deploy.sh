#! /bin/sh
set -eux

H=root@games.alexecollins.com
D=/var/phoebus-games
U=gf

ssh $H "[ -e $D ] || mkdir $D"
ssh $H "adduser --system $U || true"

rsync -avz --progress target/$U.jar $H:/var/phoebus-games/$U.jar

ssh $H "echo > $D/$U.conf && chmod +x $D/$U.jar && ln -sf $D/$U.jar /etc/init.d/$U && chown $U /etc/init.d/$U && /etc/init.d/$U restart"
