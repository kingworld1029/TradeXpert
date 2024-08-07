import React from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "@/components/ui/avatar";

const AssetTable = () => {
  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="w-[100px]">COIN</TableHead>
          <TableHead>SYMBOL</TableHead>
          <TableHead>VOLUME</TableHead>
          <TableHead>MARKET CAP</TableHead>
          <TableHead>24H</TableHead>
          <TableHead className="text-right">PRICE</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {[1, 1, 1, 1, 1, 1].map((item, index) => (
          <TableRow key={index}>
            <TableCell className="font-medium flex items-center gap-2">
              <Avatar className="-z-50">
                <AvatarImage src="https://cdn-icons-png.flaticon.com/128/5128/5128439.png" />
              </Avatar>
              <span>Bitcoin</span>
            </TableCell>
            <TableCell>BTC</TableCell> 
            <TableCell>912449199</TableCell>
            <TableCell>1203030020299</TableCell>
            <TableCell>+20.08</TableCell>
            <TableCell className="text-right">$2500.00</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
};

export default AssetTable;
