
目錄架構說明

db - 資料庫 及 Dao
di - dagger 相依注入設定
model - api傳送 及 資料庫存的資料
network - retrofit api設定
ui - activity 、adapter 及相關ui設定
util - 工具類class
viewmodel - viewmodel 的class
data : 存放Repostory 及 其他資料

GitHubAppliction 放到最上層，應用程式設定。


ui目錄說明 及viewmodel配合:

1.UsersActivity
開啟app時，首先會顯示 UsersActivity，顯示Git hub使用者列表， viewmodel是 UsersViewModel
並使用ConcatAdapter的方式, 同時用二個adapter組合 ( UsersAdapter 負責 使用者列表顯示，LoadingAdapter 負責
Loading顯示), 如果完全沒載入資料且連線失敗，會顯示 error訊息，有按鈕可以請使用者重載(重新連網)

2.UserDetailActivity
在使用者列表，點擊使用者後，顯示該使用者的資訊頁，viewmodel是 UserDetailViewModel
載入失敗會顯示 error訊息，有按鈕可以請使用者重載(重新連網)

此二頁都有使用room 資料庫為本地緩存使用，並讓LiveData有統一確認管道(異動即更新)，故讓room和Retrofit互相配合。
使用 LoadState 自定義資料結構，存放載入完成、載入中 及載入異常的情況。

並使用dagger讓需重複注入的元件統一處理，讓程式架構較簡潔

載圖是使用第三方 Glide方便載圖，並可以幫忙將圖弄成圓形


