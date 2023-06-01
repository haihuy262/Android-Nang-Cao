package huynhph30022.fpoly.src.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import huynhph30022.fpoly.src.dao.SanPhamDAO;

public class SanPhamProvider extends ContentProvider {
    // 1.Định nghĩa URI
    public static final String AUTHOR_URI = "huynhph30022.fpoly.src.provider.SanPhamProvider";
    public static final String TABLE_SAN_PHAM = "SanPham";
    String TAG = "nguyenhaihuy260";
    UriMatcher uriMatcher; // Xác định URI các định danh, địa chỉ truy cập tới
    SanPhamDAO sanPhamDAO;
    Cursor cursor;

    @Override
    public boolean onCreate() {
        Log.e(TAG, "onCreate: Khởi tạo Provider");
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHOR_URI, TABLE_SAN_PHAM, 1);
        uriMatcher.addURI(AUTHOR_URI, TABLE_SAN_PHAM + "/#", 2);

        sanPhamDAO = new SanPhamDAO(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Hàm này dùng đọc dữ liệu
        int code_uri = uriMatcher.match(uri);
        switch (code_uri) {
            case 1:
                cursor = sanPhamDAO.getAllProvider(projection, selection, selectionArgs, sortOrder);
                break;
            case 2:
                String strWhere = "id = " + uri.getPathSegments().get(1);
                cursor = sanPhamDAO.getAllProvider(projection, strWhere, selectionArgs, sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
