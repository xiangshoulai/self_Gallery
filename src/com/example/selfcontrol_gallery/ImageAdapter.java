package com.example.selfcontrol_gallery;

import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private int[] imageIds;
	private ImageView images[];

	/*
	 * ��ʼ������
	 */
	public ImageAdapter(Context context, int[] imageIds) {
		this.context = context;
		this.imageIds = imageIds;
		images = new ImageView[imageIds.length];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageIds[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return images[position];
	}

	public void CreatBitMap() {
		int reflectionGap = 4;
		int index = 0;
		for (int viewID : imageIds) {
			// ��ȡԭͼƬ������ԭͼƬ
			Bitmap resourceBitmap = BitmapFactory.decodeResource(
					context.getResources(), viewID);
			// ԭͼƬ�Ŀ�͸�
			int height = resourceBitmap.getHeight();
			int width = resourceBitmap.getWidth();

			// ���ɵ�ӰͼƬ
			Matrix matrix = new Matrix();// ����ͼƬ��ʾ����ʽ
			matrix.setScale(1, -1);
			Bitmap reflectionBitmap = Bitmap.createBitmap(resourceBitmap, 0,
					height / 2, width, height / 2, matrix, false);

			// �ϳ�ͼƬ
			Bitmap bitmap = Bitmap.createBitmap(width, height + height / 2,
					Config.ARGB_8888);
			// ��������
			Canvas canvas = new Canvas(bitmap);
			// ����ԭͼ
			canvas.drawBitmap(resourceBitmap, 0, 0, null);

			// ����ԭͼƬ�͵�ӰͼƬ֮��ļ��
			Paint defaultPaint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap,
					defaultPaint);

			// ���Ƶ�ӰͼƬ
			canvas.drawBitmap(reflectionBitmap, 0, height + reflectionGap, null);

			// ����
			Paint paint = new Paint();

			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

			// ���Խ���
			LinearGradient shader = new LinearGradient(0, height, 0,
					bitmap.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
			paint.setShader(shader);
			canvas.drawRect(0, height, width, bitmap.getHeight(), paint);
			BitmapDrawable bd = new BitmapDrawable(bitmap);
			bd.setAntiAlias(true);// ʹͼƬƽ��û�о޴�

			ImageView imageView = new ImageView(context);
			imageView.setImageDrawable(bd);

			imageView.setLayoutParams(new xsl_gallery.LayoutParams(160, 240));

			images[index++] = imageView;
		}
	}
}
