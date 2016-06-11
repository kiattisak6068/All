<?php

Route::get('/',
  array('as' => '/',
  'uses' => 'PagesController@getIndex')
);

  Route::get('about',
    array('as' => 'about',
    'uses' => 'PagesController@getAbout'));

    Route::get('contact',
      array('as' => 'contact',
      'uses' => 'PagesController@getContact'));

Route::resource('product', 'ProductController');
