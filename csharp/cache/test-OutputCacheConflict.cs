// License: LGPL-3.0 License (c) security-code-scan
using System.Web.Mvc;
using ActionResult = Microsoft.AspNetCore.Mvc.ActionResult;
using Controller = Microsoft.AspNetCore.Mvc.Controller;

[Microsoft.AspNetCore.Authorization.Authorize]
public class OutputCacheConflict: Controller
{
    [OutputCache]
    public ActionResult Index()
    {
        return null;
    }

    public ActionResult Index2()
    {
        return null;
    }
}
