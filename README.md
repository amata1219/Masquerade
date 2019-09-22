# *Masquerade*
>InventoryUIを簡潔な記述で構築可能にするライブラリです。  
>MaybeモナドやSafeCast、Tuple、Reflection等の便利クラスを同梱しています。

# 環境
- Java8
- Spigot 1.13.2

# 例
```Java
//InventoryUIを実装したクラスを定義する
public class SampleUI implements InventoryUI {

    public static void example(Player player){
        //open()メソッドでUIを開かせる
        new SampleUI().open(player);
    }

    @Override
    public Function<Player, Layout> layout() {
        //InventoryUIのbuild()メソッドを使いUIを構築する
        //Linesで何段表示するか指定出来る(InventoryTypeでの指定も可能)
        //playerはUIが表示されているプレイヤーを指す
        return build(Lines.x2, (player, l) -> {
            //インベントリのタイトル
            l.title = "Masqurade Inventory UI";

            //未設定のスロットに適用される設定
            l.defaultSlot(s -> {
                s.icon(i -> {
                    //名前無しの薄灰色ガラスはインベントリの背景色と同化するのでオススメ
                    i.material = Material.LIGHT_GRAY_STAINED_GLASS_PANE;
                    i.displayName = " ";
                });

                //このスロットをクリックした時に実行される処理
                s.onClick(e -> player.sendMessage("Hi!"));
            });

            //0番目のスロットに適用される設定
            l.put(s -> {
                s.icon(i -> {
                    //見た目
                    i.material = Material.CHAINMAIL_HELMET;

                    //耐久値
                    i.damage = 128;

                    //表示名
                    i.displayName = "Your Status";

                    //説明文
                    i.lore(
                        "Level @ " + player.getLevel(),
                        //装飾コードや複数の結合演算を用いる場合はTextを利用すると楽に記述出来る
                        //視認性向上の為に装飾コードは&とし、その左右1文字分には-を置けるがそれは実際には表示されない
                        //§fRank in 0 §7@ §b0
                        Text.of("&f-Rank in %s &7-@ &b-%s").color().format(player.getWorld().getName(), player.getLevel()).toString()
                    );

                    //エンチャントの追加
                    i.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5);

                    //アイテムフラグの追加
                    i.flag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);

                    //上記の設定を適用したItemStackを編集する
                    i.raw = item -> item.getItemMeta().setUnbreakable(true);
                });

                //このスロットのクリック時にサウンドを再生する
                s.onClick(e -> playSound(player, Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1));
            }, 0);

            //1から3番目までのスロットに適用される設定
            l.put(s -> {
                s.icon(i -> {
                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setOwningPlayer(player);
                    skull.setItemMeta(meta);

                    //ItemStackをベースアイテムとする
                    i.basedItemStack = skull;
                });
                //複数のスロット番号を指定する場合はIntStreamを使うと良い
            }, IntStream.range(1, 4));

            //スロット番号4にアニメーションスロットとして適用され、20ticksの間隔でアイコンが更新される
            l.put(20, s -> {
                //defでデフォルト状態を、after群でアニメーションを定義する
                //UIが開かれた時に0フレーム目の変更が適用され、以後20ticks単位で次のフレームの変更が適用される
                s.def(i -> {
                    //エンチャントオーラを纏わせる
                    i.gleam();
                //0フレーム目
                }).after(i -> {
                    i.material = Material.WHITE_STAINED_GLASS;
                    i.amount = 1;

                //1フレーム目
                }).after(i -> {
                    i.material = Material.LIGHT_GRAY_STAINED_GLASS;
                    i.amount = 2;

                //2フレーム目
                }).after(i -> {
                    i.material = Material.GRAY_STAINED_GLASS;
                    i.amount = 3;
                });

                //通常スロットと同様にクリック時の処理を設定出来る
                s.onClick(e -> player.teleport(player.getLocation().add(0, 10, 0)));
            }, 4);

            //onOpen()で再生、onClose()で終了させる事でUIの表示中に限りサウンドを再生する例
            //UIが開かれた時に実行される処理
            l.onOpen(e -> playSound(player, Sound.MUSIC_DISC_MELLOHI, 1, 1));

            //UIが閉じられた時に実行される処理
            l.onClose(e -> player.stopSound(Sound.MUSIC_DISC_MELLOHI));

            //UIの表示中のクリック時に実行される処理
            l.onClick(e -> {
                //インベントリ外か判定する事も可能
                if(e.isOutOfInventory()) playSound(player, Sound.BLOCK_ANVIL_PLACE, 1, 1);
            });
        });
    }
```