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
	private int maxRotateAngle = 50;// 旋转角度
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

	// 得到图片中心点位置
	private int centerOfView(View view) {

		return view.getLeft() + view.getWidth() / 2;
	}

	// Gallery图片展示中点
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
			// 图片在中心位置
			transformationBitmap((ImageView) child, t, rotateAngle);
		} else {
			// 旋转角度
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

	// 图形变化核心方法
	private void transformationBitmap(ImageView child, Transformation t,
			int rotateAngle) {
		// TODO Auto-generated method stub

		camera.save();// 记录图片变形效果

		Matrix imageMatrix = t.getMatrix();
		int rotate = Math.abs(rotateAngle);
		int width = child.getWidth();
		int height = child.getHeight();
		camera.translate(0.0f, 0.0f, 100.0f);

		if (rotate < maxRotateAngle) {

			float zoom = (float) (rotate * 1.5 + maxZoom);
			// 图片缩放
			camera.translate(0.0f, 0.0f, zoom);
			// 图片透明度
			child.setAlpha((int) (255 - rotate * 2.5));
		}

		// X
		// camera.rotateX(rotateAngle);
		// z轴
		camera.rotateZ(rotateAngle);
		// 以y轴 进行旋转
		camera.rotateY(rotateAngle);

		camera.getMatrix(imageMatrix);// 将图片的效果交给imageMatrix

		// Preconcats matrix相当于右乘矩阵
		// Postconcats matrix相当于左乘矩阵。
		// imageMatrix.preTranslate(-(imageWidth/2), -(imageHeight/2));
		// imageMatrix.postTranslate((imageWidth/2), (imageHeight/2));
		/*
		 * 这两行代码意思可能就不那么明显了，先说如果不加这两行代码，会是一个什么情况， 默认情况下，动画是以对象的左上角为起点的，如果这样的话，
		 * 动画的效果就变成了可见对象在它的左上角开始，逐渐向右下角扩大，这显然不是我们期望的。
		 * 所以我们前面用到的halfWidth，halfHeight就用到了，这里保存了可见对象的一半宽度和高度，也就是中点，
		 * 使用上面这两个方法后，就会改变动画的起始位置，动画默认是从右下角开始扩大的，
		 * 使用matrix.preTranslate(-halfWidth, -halfHeight) 就把扩散点移到了中间，
		 * 同样，动画的起始点为左上角，使用matrix.postTranslate(halfWidth,
		 * halfHeight)就把起始点移到了中间， 这样就实现我们期望的效果了。
		 */

		imageMatrix.preTranslate(-(width / 2), -(height / 2));
		imageMatrix.postTranslate(width / 2, height / 2);

		camera.restore();// 还原

	}
}
