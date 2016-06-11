@extends('layouts.layout')

@section('title')
   ALL Product
@stop
<style>
table {
    border-collapse: collapse;
    width: 50%;
}

th, td {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}

tr:hover{background-color:#f5f5f5}                                                                                                                                                               
</style>
@section('body')
   <h2 style="margin-left:5%">List Product</h2>
   <div style="margin-left:5%">
     <a href="{{route('product.create')}}">[Add Product]</a>
     <table style="margin-top:1%">
       <tr>
         <td>
           #
         </td>
         <td>
           <b>Name</b>
         </td>
         <td>
           <b>Price</b>
         </td>

       </tr>
     <tr>
<?php
$i=1;
 ?>
        @foreach ($product as $data)
        <td>
          <?=$i++?>
        </td>
        <td>
          <a href="{{ URL::to('product/'.$data->id) }}">{{$data->name}}</a>

        </td>
         <td>
           {{$data->price}}$
         </td>

         </tr>
        @endforeach
        </table>
        <br>

   </div>
@stop
