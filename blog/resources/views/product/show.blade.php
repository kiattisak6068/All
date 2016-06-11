@extends('layouts.layout')

@section('title')
{{$product->name}}
@stop

@section('body')
{!!Form::open([
  'method'=>'delete',
  'route'=>['product.destroy',$product->id]
  ])!!}
<div style="margin-left:5%">
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
  <table>
    <tr>
      <td>
        <b>Name</b>
      </td>
      <td>
        <b>Price</b>
      </td>
      <td>
        <b>Option</b>
      </td>
    </tr>
    <tr>
      <td>
        {{$product->name}}
      </td>
      <td>
        {{$product->price}}
      </td>
      <td>
        <input type="button" onclick="location.href='{{route('product.edit',$product->id)}}';" value="Edit" />
        {!!Form::submit('Delete')!!}
      </td>
    </tr>
  </table>
</div>


{!!Form::close()!!}
@stop
