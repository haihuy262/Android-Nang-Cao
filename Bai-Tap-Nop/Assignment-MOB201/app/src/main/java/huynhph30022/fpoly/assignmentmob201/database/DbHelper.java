package huynhph30022.fpoly.assignmentmob201.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "quanlyungdung.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE IF NOT EXISTS User(soDienThoai TEXT PRIMARY KEY, matKhau TEXT)";
        db.execSQL(createTableUser);
        String createTableMusic = "CREATE TABLE IF NOT EXISTS Music(id INTEGER PRIMARY KEY AUTOINCREMENT, tenBaiHat TEXT, link TEXT)";
        db.execSQL(createTableMusic);

        db.execSQL("INSERT INTO Music VALUES" +
                "(1,'Dĩ vẵng cuộc tình','https://nhacchuong123.com/nhac-chuong/abcdefgh/nhac-chuong-dua-hoi-qua-le-bao-binh.mp3')," +
                "(2,'Ngày Mai Người Ta Lấy Chồng','https://nhacchuong123.com/nhac-chuong/abcdefgh/ngay-mai-nguoi-ta-lay-chong-lofi-thanh-dat.mp3')," +
                "(3,'Có Ai Hẹn Hò Cùng Em Chưa','https://nhacchuong123.com/nhac-chuong/abcdefgh/Nhac-Chuong-Co-Ai-Hen-Ho-Cung-Em-Chua-Quan-AP.mp3')," +
                "(4,'Thiệp Hồng Người Dưng','https://nhacchuong123.com/nhac-chuong/am-thanh/thiep-hong-nguoi-dung.mp3')," +
                "(5,'Thuận Theo Ý Trời','https://nhacchuong123.com/nhac-chuong/amthanh/Nhac-Chuong-Thuan-Theo-Y-Troi-Bui-Anh-Tuan.mp3')," +
                "(6,'Nhớ Lắm Đấy','https://nhacchuong123.com/nhac-chuong/abcdefgh/bao-nhieu-thuong-nho-cho-vua-hoailam.mp3')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS Music");
            onCreate(db);
        }
    }
}
