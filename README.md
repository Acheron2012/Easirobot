# Easirobot
“智能孝子机器人后台”
层级框架如下<br />
![Image text](https://raw.githubusercontent.com/Acheron2012/Easirobot/master/img-folder/Hierarchical.png)

更新项目（新加了文件）：<br />
$cd ~/easirobot<br />
$git add .                  //这样可以自动判断新加了哪些文件，或者手动加入文件名字<br />
commit -m "my first vesion of ..."              //提交到本地仓库，不加参数会提示<br />                 
$git remote add origin git@github.com:Acheron2012/Easirobot.git        //增加到remote<br />
$git push origin master    //不是新创建的，不用再add 到remote上了<br />
<br />
//远程更新了代码<br />
$git pull origin master<br />
<br />
clone代码到本地：<br />
$git clone  git@github.com:Acheron2012/Easirobot.git<br />
假如本地已经存在了代码，而仓库里有更新，把更改的合并到本地的项目：<br />
$git fetch origin    //获取远程更新<br />
$git merge origin/master //把更新的内容合并到本地分支<br />
<br />
撤销<br />
$git reset<br />
