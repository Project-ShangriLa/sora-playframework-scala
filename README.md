#Sora 穹

ShangriLa API Server

## ShangriLa API Server システム概要

### 説明

アニメ作品の情報を返すREST形式のAPIサーバーです。


### サーバーシステム要件

* Java7+
* フレームワーク: Play Framework 2.3+
* 言語: Scala 2.11+

### インストール

* mysql or mariadb インストール
* anime_admin_development データベース作成
* マスターテーブルのインサート
* activator インストール

### 起動方法

```
shell> activator
activator shell> start 80
```

### ライセンス

Apache 2 license

## V1 API リファレンス

### エンドポイント

http://api.moemoe.tokyo/anime/v1

### 認証

V1では認証を行いません。


### レートリミット

なし

### GET /anime/v1/master/cours

ShangriLa API Serverが持っているアニメ情報のクールごとの情報のリストを返却します。

#### Request Body

なし

#### Response Body

| Property     | Value               |description|Sample|
| :------------ | :------------------ |:------|:-------|
| cours_idの値 | Cours Object |cours_idはシステムで割り振ったクールごとのユニークなID(coursマスターのID)|1|

##### Cours Object

| Property     | Value               |description|Sample|
| :------------ | :------------------ |:------|:-------|
| id | Number  |cours_id|6|
| year| Number |該当する西暦(YYYY)|2015|
| cours| Number |yearの中での順番[１〜４]|2|


レスポンス例

```
 $curl http://api.moemoe.tokyo/anime/v1/master/cours | jq .

{
  "4": {
    "id": 4,
    "year": 2014,
    "cours": 4
  },
  "5": {
    "id": 5,
    "year": 2015,
    "cours": 1
  },
  "6": {
    "id": 6,
    "year": 2015,
    "cours": 2
  }
}
```


### GET /aime/v1/master/:year

YYYY年アニメ1クール4クールの情報をすべて返却します

#### Request Body

なし

#### Response Body

| Property     | Value               |description|Sample|
| :------------ | :------------------ |:--------|:-------|
| Array    |Base Object|後述|

##### Base Object

| Property     | Value               |description|Sample|
| :------------ | :------------------ |:--------|:-------|
| id    |Number|APIで管理するアニメ作品に割り当てられているユニークなID|125|
| title    |String|アニメ作品名|"冴えない彼女の育てかた"|


レスポンス例

```
curl http://api.moemoe.tokyo/anime/v1/master/2015 | jq .
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

#### Request Body

なし

#### Response Body


| Property     | Value               |description|Sample|
| :------------ | :------------------ |:--------|:-------|
| Array    |Base Object|後述||

##### Base Object

requiredに◯がないものは値なし(=データメンテナンスしていない)の場合があります。

またプロパティは追加される可能性があります。

| Property     |Value |Required|description|Sample|
| :------------|:-----|:-------|:----------|:-----|
| id           |Number|◯|APIで管理するアニメ作品に割り当てられているユニークなID|125|
| title        |String|◯|アニメ作品名|"冴えない彼女の育てかた"|
| title_short1 |String|-|アニメ作品名の略称1|"冴えカノ"|
| title_short2 |String|-|アニメ作品名の略称2||
| title_short3 |String|-|アニメ作品名の略称3||
| public_url   |String|◯|アニメ作品の公式URL|"http://www.saenai.tv/"|
| twitter_account|String|◯|ツイッターアカウント|"saenai_heroine"|
| twitter_hash_tag|String|◯|ツイッターハッシュタグ|"saekano"|
| cours_id     |Number|◯|coursマスターのID|5|
| created_at   |String|◯|データの作成日時|"2015-01-08 09:37:01"|
| updated_at   |String|◯|データの更新日時|"2015-01-08 09:39:04"|
| sex          |Number|-|男性向け=0, 女性向け=1, メンテナンスしてないor両性の場合=NULL|0|
| sequel       |Number|-|続編モノの場合は1以上の数値が入る|0|

レスポンス例

```
curl http://api.moemoe.tokyo/anime/v1/master/2015/2 | jq .
[
  {
    "title_short2": "",
    "twitter_account": "shokugeki_anime",
    "public_url": "http://shokugekinosoma.com/",
    "title_short1": "ソーマ",
    "twitter_hash_tag": "shokugeki_anime",
    "id": 176,
    "cours_id": 6,
    "title": "食戟のソーマ",
    "title_short3": ""
  },
  {
    "title_short2": "",
    "twitter_account": "danmachi_anime",
    "public_url": "http://danmachi.com/",
    "title_short1": "ダンまち",
    "twitter_hash_tag": "danmachi",
    "id": 177,
    "cours_id": 6,
    "title": "ダンジョンに出会いを求めるのは間違っているだろうか",
    "title_short3": ""
  }  
]
```



