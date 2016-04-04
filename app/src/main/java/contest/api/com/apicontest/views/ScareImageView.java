package contest.api.com.apicontest.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by floriantorel on 04/03/16.
 */
public class ScareImageView extends ImageView {

    public ScareImageView(Context context) {
        super(context);
    }

    public ScareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure( widthMeasureSpec , heightMeasureSpec );

        long w = getMeasuredWidth();

        int a = (int) w * 9;
        int b = a / 13;

        long c = ( getMeasuredWidth() * 9 ) / 13;

        setMeasuredDimension( getMeasuredWidth() , (int) c );

    }
}
