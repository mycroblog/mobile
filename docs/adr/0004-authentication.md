# ADR-0004: 認証とアカウント

- Status: Accepted
- Date: 2026-11-27
- Deciders: @rosylilly

## Decision (決定)

### 1. サーバーの決定に従う

OAuth の扱いは、サーバーの決定 (my ADR-0090 §3) に従う。my ADR-0090 §3 の公式のモバイルアプリの記述には、まだ Tauri の前提が残っている。ネイティブのアプリに合わせる書き直しは my#3569 で扱い、それまでは、この ADR の §2 を端末の側の決定とする。

- 公式のモバイルアプリは CIMD のクライアントで、`client_id` はプロジェクトのサイトに置いた URL である。
- redirect URI には claimed `https` (Universal Links と App Links) を使い、PKCE を付ける。
- サーバーは同意の画面を毎回出す。アプリはそれを省こうとしない。

### 2. 端末での扱い

- 認可の画面は、iOS は `ASWebAuthenticationSession`、Android は Custom Tabs で開く。WebView では開かない。WebView は、アプリが利用者の資格情報を見られる形になるからである。
- トークンは iOS は Keychain、Android は Keystore で守った領域に置く。

### 3. 複数のサーバーと複数のアカウント

最初から、複数のサーバーにまたがる複数のアカウントを持てる設計にする。ログインのときに、利用者がサーバーのドメインを入れる。アカウントを 1 つに決め打ちした設計は、後から広げるのが高くつくからである。

### 4. サイトに置くもの

`client_id` の文書、`apple-app-site-association`、`assetlinks.json` は、プロジェクトのサイトに置く。置き場所と配信の方法は、ログインを実装するときに決める。

## 採らなかった案

- private-use URI scheme の redirect: サイトに何も置かずに済む。しかし OS がスキームの持ち主を検証しないので、ほかのアプリが応答を横取りできる (my ADR-0090 §3)。

## 見直す条件

- my ADR-0090 §3 の結論が変わったとき。
