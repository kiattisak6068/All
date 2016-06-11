@extends('layouts.layout')

@section('title')
Edit {{$product->name}}
@stop

@section('body')
<div style="margin-left:5%">
  {!!Form::model($product,[
    'method'=>'patch',
    'route'=>['product.update',$product->id]
    ]) !!}

    {!!Form::label('Name: ')!!}
    {!!Form::text('name',$product->name,['placeholder'=>"give a nanme"])!!}
  <br>
    {!!Form::label('Price: ')!!}
    {!!Form::text('price',$product->price,['placeholder'=>"give a price"])!!}

  {!!Form::submit('Edit')!!}
  {!!Form::close()!!}
</div>
@stop
