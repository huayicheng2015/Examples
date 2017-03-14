package com.medzone.greendao;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.Range;
import android.util.Rational;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.medzone.greendao.bean.Student;
import com.medzone.greendao.dao.StudentDao;
import com.medzone.greendao.util.MathUtil;

import org.greenrobot.greendao.rx.RxDao;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private PopupMenu pmMenu;
    private TextView tvHello, tvSave, tvCount;
    private AppBarLayout ablAppBar;
    private CollapsingToolbarLayout ctlToolBar;
    private RxDao<Student, Long> rxDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StudentDao studentDao = ((App) getApplication()).getDaoSession().getStudentDao();
        rxDao = new RxDao<>(studentDao);

        ctlToolBar = (CollapsingToolbarLayout) findViewById(R.id.ctl_toolbar);
        ablAppBar = (AppBarLayout) findViewById(R.id.apl_appbar);
        tvHello = (TextView) findViewById(R.id.tv_hello);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvCount = (TextView) findViewById(R.id.tv_count);
        pmMenu = new PopupMenu(this, tvHello, Gravity.BOTTOM | Gravity.END);
        pmMenu.inflate(R.menu.menu_operation);

        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pmMenu.show();
                Intent it = new Intent(MainActivity.this, CubicBezierActivity.class);
//                Intent it = new Intent(MainActivity.this, BezierActivity.class);
                MainActivity.this.startActivity(it);
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setName("LiLei");
                rxDao.insert(student).subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        Toast.makeText(MainActivity.this, student.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDao.count().subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Toast.makeText(MainActivity.this, aLong.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        pmMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        if (ctlToolBar.isTitleEnabled()) {
            ablAppBar.setExpanded(false);
        }

        Observable.create(new Observable.OnSubscribe<Range<Integer>>() {
            @Override
            public void call(Subscriber<? super Range<Integer>> subscriber) {
                Range<Integer> range1 = Range.create(1, 10);
                Range<Integer> range2 = Range.create(2, 11);
                subscriber.onNext(MathUtil.intersectionOf(range1, range2));
                subscriber.onCompleted();
            }
        }).map(new Func1<Range<Integer>, List<Integer>>() {
            @Override
            public List<Integer> call(Range<Integer> range) {
                List<Integer> intList = new ArrayList<>();
                for (int i = range.getLower(); i <= range.getUpper(); i++) {
                    intList.add(i);
                }
                return intList;
            }
        }).flatMap(new Func1<List<Integer>, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(List<Integer> integers) {
                return Observable.from(integers);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i(getClass().getSimpleName(), "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(getClass().getSimpleName(), "Error");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(getClass().getSimpleName(), String.valueOf(integer));
                    }
                });

        Range<Integer> range = MathUtil.intersectionOf(Range.create(1, 10), Range.create(2, 11));
        Observable.range(range.getLower(), range.getUpper() - range.getLower() + 1).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i(getClass().getSimpleName(), "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(getClass().getSimpleName(), "Error");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(getClass().getSimpleName(), String.valueOf(integer));
                    }
                });

        Rational rational1 = new Rational(1, 0);
        Rational rational2 = new Rational(4, 0);
        Log.i(getClass().getSimpleName(), MathUtil.plus(rational1, rational2).toString());
        Log.i(getClass().getSimpleName(), MathUtil.plus(Rational.POSITIVE_INFINITY, Rational.NEGATIVE_INFINITY).toString());
        Log.i(getClass().getSimpleName(), Rational.parseRational("10/5").toString());
    }

}
