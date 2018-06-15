package com.qinlei.tokephoto;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.qinlei.tokephoto.callback.AttachCallBack;
import com.qinlei.tokephoto.callback.TokePhotoCallBack;
import com.qinlei.tokephoto.delegate.DelegateFragment;
import com.qinlei.tokephoto.delegate.DelegateFragmentFinder;

public class TokePhotoUtils {
    private static class Singleton {
        private static final TokePhotoUtils instance = new TokePhotoUtils();
    }

    private TokePhotoUtils() {

    }

    public static TokePhotoUtils getInstance() {
        return Singleton.instance;
    }

    public void captureCamera(@NonNull FragmentActivity activity, @NonNull final TokePhotoCallBack callback) {
        final DelegateFragment delegate = findDelegate(activity);
        if (delegate != null) {
            delegate.setAttachCallBack(new AttachCallBack() {
                @Override
                public void onAttach() {
                    delegate.captureCamera(callback);
                }
            });
        }
    }

    public void captureCamera(@NonNull Fragment fragment, @NonNull final TokePhotoCallBack callback) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null && !activity.isFinishing()) {
            final DelegateFragment delegate = findDelegate(activity);
            if (delegate != null) {
                delegate.setAttachCallBack(new AttachCallBack() {
                    @Override
                    public void onAttach() {
                        delegate.captureCamera(callback);
                    }
                });
            }
        }
    }

    public void captureCameraForSquare(@NonNull FragmentActivity activity, @NonNull final TokePhotoCallBack callback) {
        final DelegateFragment delegate = findDelegate(activity);
        if (delegate != null) {
            delegate.setAttachCallBack(new AttachCallBack() {
                @Override
                public void onAttach() {
                    delegate.captureCameraForSquare(callback);
                }
            });
        }
    }

    public void captureCameraForSquare(@NonNull Fragment fragment, @NonNull final TokePhotoCallBack callback) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null && !activity.isFinishing()) {
            final DelegateFragment delegate = findDelegate(activity);
            if (delegate != null) {
                delegate.setAttachCallBack(new AttachCallBack() {
                    @Override
                    public void onAttach() {
                        delegate.captureCameraForSquare(callback);
                    }
                });
            }
        }
    }

    public void captureCameraForCrop(@NonNull FragmentActivity activity, @NonNull final TokePhotoCallBack callback, final int aspectX, final int aspectY) {
        final DelegateFragment delegate = findDelegate(activity);
        if (delegate != null) {
            delegate.setAttachCallBack(new AttachCallBack() {
                @Override
                public void onAttach() {
                    delegate.captureCameraForCrop(callback, true, aspectX, aspectY);
                }
            });
        }
    }

    public void captureCameraForCrop(@NonNull Fragment fragment, @NonNull final TokePhotoCallBack callback, final int aspectX, final int aspectY) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null && !activity.isFinishing()) {
            final DelegateFragment delegate = findDelegate(activity);
            if (delegate != null) {
                delegate.setAttachCallBack(new AttachCallBack() {
                    @Override
                    public void onAttach() {
                        delegate.captureCameraForCrop(callback, true, aspectX, aspectY);
                    }
                });
            }
        }
    }

    public void captureGallery(@NonNull FragmentActivity activity, @NonNull final TokePhotoCallBack callback) {
        final DelegateFragment delegate = findDelegate(activity);
        if (delegate != null) {
            delegate.setAttachCallBack(new AttachCallBack() {
                @Override
                public void onAttach() {
                    delegate.captureGallery(callback);
                }
            });
        }
    }

    public void captureGallery(@NonNull Fragment fragment, @NonNull final TokePhotoCallBack callback) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null && !activity.isFinishing()) {
            final DelegateFragment delegate = findDelegate(activity);
            if (delegate != null) {
                delegate.setAttachCallBack(new AttachCallBack() {
                    @Override
                    public void onAttach() {
                        delegate.captureGallery(callback);
                    }
                });
            }
        }
    }

    public void captureGalleryForSquare(@NonNull FragmentActivity activity, @NonNull final TokePhotoCallBack callback) {
        final DelegateFragment delegate = findDelegate(activity);
        if (delegate != null) {
            delegate.setAttachCallBack(new AttachCallBack() {
                @Override
                public void onAttach() {
                    delegate.captureGalleryForSquare(callback);
                }
            });
        }
    }

    public void captureGalleryForSquare(@NonNull Fragment fragment, @NonNull final TokePhotoCallBack callback) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null && !activity.isFinishing()) {
            final DelegateFragment delegate = findDelegate(activity);
            if (delegate != null) {
                delegate.setAttachCallBack(new AttachCallBack() {
                    @Override
                    public void onAttach() {
                        delegate.captureGalleryForSquare(callback);
                    }
                });
            }
        }
    }

    public void captureGalleryForCorp(@NonNull FragmentActivity activity, @NonNull final TokePhotoCallBack callback, final int aspectX, final int aspectY) {
        final DelegateFragment delegate = findDelegate(activity);
        if (delegate != null) {
            delegate.setAttachCallBack(new AttachCallBack() {
                @Override
                public void onAttach() {
                    delegate.captureGalleryForCorp(callback, true, aspectX, aspectY);
                }
            });
        }
    }

    public void captureGalleryForCorp(@NonNull Fragment fragment, @NonNull final TokePhotoCallBack callback, final int aspectX, final int aspectY) {
        FragmentActivity activity = fragment.getActivity();
        if (activity != null && !activity.isFinishing()) {
            final DelegateFragment delegate = findDelegate(activity);
            if (delegate != null) {
                delegate.setAttachCallBack(new AttachCallBack() {
                    @Override
                    public void onAttach() {
                        delegate.captureGalleryForCorp(callback, true, aspectX, aspectY);
                    }
                });
            }
        }
    }

    private DelegateFragment findDelegate(FragmentActivity activity) {
        return DelegateFragmentFinder.getInstance().find(activity);
    }
}
