ffmpeg -i template.mp4 -r 5 image/%03d.jpg

ffmpeg -f image2 -framerate 5 -i %03d.jpg -vf ass=ss.ass,scale=180:-1 z5_sacle.gif

## 部署字体
yum install wqy-zenhei-fonts