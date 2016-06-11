<?php
namespace App\Http\Controllers;

use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Routing\Controller as BaseController;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Foundation\Auth\Access\AuthorizesRequests;

class PagesController extends BaseController
{

  public function getIndex(){
    return view('welcome');
  }
  public function getAbout(){
      $name="คณะวิทยาศาสตร์ มหาวิทยาลัยอุบลราชธานี";
    return view('page.about')->with("name",$name);
  }
  public function getContact(){
    return view('page.contact');
  }

}
