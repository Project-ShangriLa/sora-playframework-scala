#Sora 穹

ShangriLa API Server

## 概要
アニメ作品の情報を返すREST形式のAPIサーバーです。

* フレームワーク: Play Framework 2.3+
* 言語: Scala 2.11+

## インストール

記述中

* mysql or mariadb インストール
* anime_admin_development データベース作成
* マスターテーブルのインサート
* activator インストール

## 起動方法

activator run

記述中

## V1 API リファレンス

### エンドポイント

http://api.moemoe.tokyo/anime/v1

### 認証

V1では認証を行いません。


### レートリミット

なし

### GET /anime/v1/master/list

ShangriLa API Serverサーバーが持っているマスターの情報のリストを返却します。

#### Request Body

なし

#### Response Body

| Property     | Value               |description|Sample|
| :------------ | :------------------ |:------|:-------|
| Year(YYYY)        | Number Array|アニメのその年のクール番号 1から順に春夏秋冬|[1, 2, 3, 4]|

レスポンス例

```
 $curl http://api.moemoe.tokyo/anime/v1/master/list | jq .
 
 {
  '2014' : [1,2,3,4],
  '2015' : [1,2]
 }
```


### GET /aime/v1/master/:year

YYYY年アニメ1クール4クールの情報をすべて返却します

#### Request Body

なし

#### Response Body

| Property     | Value               |description|Sample|
| :------------ | :------------------ |:--------|:-------|
| Array    |Base object|後述|

##### Base Object

| Property     | Value               |description|Sample|
| :------------ | :------------------ |:--------|:-------|
| id    |Number|APIで管理するアニメ作品に割り当てられているユニークなID|125|
| title    |String|アニメ作品名|"冴えない彼女の育てかた"|


レスポンス例

```
curl http://localhost:9000/anime/v1/master/2015 | jq .
[
  {
    "id": 124,
    "title": "幸腹グラフィティ"
  },
  {
    "id": 125,
    "title": "銃皇無尽のファフニール"
  },
  {
    "id": 126,
    "title": "冴えない彼女の育てかた"
  },
  {
    "id": 127,
    "title": "暗殺教室"
  },
  {
    "id": 129,
    "title": "探偵歌劇ミルキィホームズTD"
  }
]
```


### GET /anime/v1/master/:year/:n

YYYY年アニメのうちの指定されたクールの情報をすべて返します

レスポンス例:curl



### GET /anime/v1/twitter_follwer/:year/:n

YYYY年アニメのうち指定されたクールのツイッターフォロワー数の情報を返します

レスポンス例:curl

