package com.example.selfcontrol_gallery;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class xsl_gallery extends Gallery {

	private int maxZoom = -250;
	private int maxRotateAngle = 50;// ��ת�Ƕ�
	private int centerOfGallery;

	private Camera camera = new Camera();

	public xsl_gallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		setStaticTransformationsEnabled(true);
	}

	public xsl_gallery(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		setStaticTransformationsEnabled(true);
	}

	public xsl_gallery(Context context, AttributeSet attris) {
		super(context, attris);
		setStaticTransformationsEnabled(true);
	}

	// �õ�ͼƬ���ĵ�λ��
	private int centerOfView(View view) {

		return view.getLeft() + view.getWidth() / 2;
	}

	// GalleryͼƬչʾ�е�
	private int centerOfGallery() {
		// setPadding(50, 0, 30, 0);
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		// TODO Auto-generated method stub
		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);
		int rotateAngle = 0;
		int centerOfChild = centerOfView(child);
		int width = child.getWidth();
		if (centerOfChild == centerOfGallery) {
			// ͼƬ������λ��
			transformationBitmap((ImageView) child, t, rotateAngle);
		} else {
			// ��ת�Ƕ�
			// rotateAngle = (int) ((float)(centerOfGallery -
			// centerOfChild)/width * maxRotateAngle);
			rotateAngle = (int) ((float) (centerOfChild - centerOfGallery)
					/ width * maxRotateAngle);

			// -50 < || > 50
			if (Math.abs(rotateAngle) > maxRotateAngle) {
				rotateAngle = rotateAngle < 0 ? -maxRotateAngle
						: maxRotateAngle;
			}

			transformationBitmap((ImageView) child, t, rotateAngle);
		}
		// return super.getChildStaticTransformation(child, t);

		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		centerOfGallery = centerOfGallery();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	// ͼ�α仯���ķ���
	private void transformationBitmap(ImageView child, Transformation t,
			int rotateAngle) {
		// TODO Auto-generated method stub

		camera.save();// ��¼ͼƬ����Ч��

		Matrix imageMatrix = t.getMatrix();
		int rotate = Math.abs(rotateAngle);
		int width = child.getWidth();
		int height = child.getHeight();
		camera.translate(0.0f, 0.0f, 100.0f);

		if (rotate < maxRotateAngle) {

			float zoom = (float) (rotate * 1.5 + maxZoom);
			// ͼƬ����
			camera.translate(0.0f, 0.0f, zoom);
			// ͼƬ͸����
			child.setAlpha((int) (255 - rotate * 2.5));
		}

		// X
		// camera.rotateX(rotateAngle);
		// z��
		camera.rotateZ(rotateAngle);
		// ��y�� ������ת
		camera.rotateY(rotateAngle);

		camera.getMatrix(imageMatrix);// ��ͼƬ��Ч������imageMatrix

		// Preconcats matrix�൱���ҳ˾���
		// Postconcats matrix�൱����˾���
		// imageMatrix.preTranslate(-(imageWidth/2), -(imageHeight/2));
		// imageMatrix.postTranslate((imageWidth/2), (imageHeight/2));
		/*
		 * �����д�����˼���ܾͲ���ô�����ˣ���˵������������д��룬����һ��ʲô����� Ĭ������£��������Զ�������Ͻ�Ϊ���ģ���������Ļ���
		 * ������Ч���ͱ���˿ɼ��������������Ͻǿ�ʼ���������½���������Ȼ�������������ġ�
		 * ��������ǰ���õ���halfWidth��halfHeight���õ��ˣ����ﱣ���˿ɼ������һ���Ⱥ͸߶ȣ�Ҳ�����е㣬
		 * ʹ�����������������󣬾ͻ�ı䶯������ʼλ�ã�����Ĭ���Ǵ����½ǿ�ʼ����ģ�
		 * ʹ��matrix.preTranslate(-halfWidth, -halfHeight) �Ͱ���ɢ���Ƶ����м䣬
		 * ͬ������������ʼ��Ϊ���Ͻǣ�ʹ��matrix.postTranslate(halfWidth,
		 * halfHeight)�Ͱ���ʼ���Ƶ����м䣬 ������ʵ������������Ч���ˡ�
		 */

		imageMatrix.preTranslate(-(width / 2), -(height / 2));
		imageMatrix.postTranslate(width / 2, height / 2);

		camera.restore();// ��ԭ

	}
}
